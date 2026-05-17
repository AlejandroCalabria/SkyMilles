package com.NexGen.nutriiftm.service;

import com.NexGen.nutriiftm.model.*;
import com.NexGen.nutriiftm.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CORREÇÃO #6: buscarPorNome é chamado UMA ÚNICA VEZ por ingrediente.
 *
 * O padrão anterior chamava buscarPorNome em salvarComCalculo() e depois de novo
 * em CalcularPreview() ao recarregar a entidade do banco — dobrando o custo O(n)
 * da busca fuzzy para cada ingrediente.
 *
 * Agora:
 *  - salvarComCalculo() faz a busca e persiste codigoTACO.
 *  - calcularPorReceita() usa o codigoTACO salvo para resolver via buscarPorCodigo()
 *    (lookup O(1) via mapa) em vez de repetir a busca fuzzy.
 */
@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepo;
    private final TACOService       tacoService;
    private final MacronutrientesService macroService;

    @Transactional(readOnly = true)
    public List<Receita> listarTodos() { return receitaRepo.findAll(); }

    @Transactional(readOnly = true)
    public Receita buscarPorId(Long id) {
        return receitaRepo.findById(id).orElseThrow(
            () -> new jakarta.persistence.EntityNotFoundException("Receita não encontrada: " + id)
        );
    }

    @Transactional
    public void deletar(Long id) { receitaRepo.deleteById(id); }

    /**
     * Salva a receita vinculando cada ingrediente ao seu ItemTACO e persistindo
     * o codigoTACO para evitar nova busca fuzzy no futuro.
     */
    @Transactional
    public Receita salvarComCalculo(Receita receita) {
        for (IngredienteReceita ing : receita.getIngredientes()) {
            ItemTACO item = tacoService.buscarPorNome(ing.getNome()); // busca fuzzy — apenas 1x
            ing.setItemTACO(item);
            if (item != null) {
                ing.setCodigoTACO(item.getCodigo());
            }
            ing.setReceita(receita);
        }
        return receitaRepo.save(receita);
    }

    /**
     * Calcula preview nutricional de uma receita já persistida.
     * Usa codigoTACO salvo para resolver ItemTACO via lookup direto,
     * evitando nova busca fuzzy.
     */
    @Transactional(readOnly = true)
    public ValoresNutricionais calcularPreview(Receita receita) {
        for (IngredienteReceita ing : receita.getIngredientes()) {
            if (ing.getItemTACO() == null && ing.getCodigoTACO() != null) {
                // Lookup direto por código — O(1) via mapa interno do TACOService
                ing.setItemTACO(tacoService.buscarPorCodigo(ing.getCodigoTACO()));
            }
            // Fallback: se o código não resolver, tenta busca fuzzy como último recurso
            if (ing.getItemTACO() == null) {
                ing.setItemTACO(tacoService.buscarPorNome(ing.getNome()));
            }
        }
        return macroService.calcular(receita);
    }

    /** @deprecated use {@link #calcularPreview(Receita)} */
    @Deprecated
    public ValoresNutricionais CalcularPreview(Receita receita) {
        return calcularPreview(receita);
    }
}