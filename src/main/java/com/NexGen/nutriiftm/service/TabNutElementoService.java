package com.NexGen.nutriiftm.service;

import com.NexGen.nutriiftm.domain.nutricional.ArredondamentoAnvisa;
import com.NexGen.nutriiftm.model.TabNutElemento;
import com.NexGen.nutriiftm.repository.TabNutElementoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Correções aplicadas:
 *  - calcularVD usa ArredondamentoAnvisa.percentualVD (guard contra -0.0 e NaN).
 *  - calcularValorPadrao centralizado em ArredondamentoAnvisa.valorPor100g.
 *  - Eliminado getTabPorcao() via TabNutElemento (nunca implementado).
 */
@Service
@RequiredArgsConstructor
public class TabNutElementoService {

    private final TabNutElementoRepository repo;

    public List<TabNutElemento> listarTodos() { return repo.findAll(); }

    public TabNutElemento buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new jakarta.persistence.EntityNotFoundException("TabNutElemento não encontrado: " + id)
        );
    }

    public void deletar(Long id) { repo.deleteById(id); }

    /**
     * Salva um TabNutElemento calculando automaticamente valorPadrao (100g) e %VD.
     *
     * @param tne    entidade com tneValor (valor por porção) já preenchido
     * @param porcaoG tamanho da porção em gramas
     */
    public TabNutElemento salvarComCalculo(TabNutElemento tne, double porcaoG) {
        double valorPorcao    = tne.getTneValor();
        double valorRecomendado = tne.getElemento().getEleValorRecomendado();

        // CORREÇÃO #2/#3: único lugar que calcula esses valores
        tne.setTneValorPadrao(ArredondamentoAnvisa.valorPor100g(valorPorcao, porcaoG));
        tne.setTneVD((double) ArredondamentoAnvisa.percentualVD(valorPorcao, valorRecomendado));

        return repo.save(tne);
    }
}