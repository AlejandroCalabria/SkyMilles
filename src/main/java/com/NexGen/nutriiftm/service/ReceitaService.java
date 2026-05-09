package com.NexGen.nutriiftm.service;

import org.springframework.stereotype.Service;

//import com.NexGen.nutriiftm.repository.IngredienteReceitaRepository;
import com.NexGen.nutriiftm.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

import com.NexGen.nutriiftm.model.IngredienteReceita;
import com.NexGen.nutriiftm.model.ItemTACO;
import com.NexGen.nutriiftm.model.Receita;
import com.NexGen.nutriiftm.model.ValoresNutricionais;

@Service
@RequiredArgsConstructor
public class ReceitaService {
    private final ReceitaRepository ReceitaRepo;
    //private final IngredienteReceitaRepository IngredienteRepo;
    private final TACOService tacoService;
    private final MacronutrientesService macroService;

    public List<Receita> listarTodos() { return ReceitaRepo.findAll(); }
    public Receita buscarPorId(Long id) { return ReceitaRepo.findById(id).orElseThrow(); }
    public void deletar(Long id) { ReceitaRepo.deleteById(id); }

    public Receita salvarComCalculo(Receita receita){
        // Para cada Ingredinte, busca o item TACO e vincula
        for(IngredienteReceita ing : receita.getIngredientes()){
            ItemTACO item = tacoService.buscarPorNome(ing.getNome());
            ing.setItemTACO(item); //@Transient não salva no banco 
            if (item != null) {
                ing.setCodigoTACO(item.getCodigo());  //esse sim salva
            }
            ing.setReceita(receita); // vincula o ingrediente à receita
        }

        //ValoresNutricionais valores = macroService.calcular(receita);
        return ReceitaRepo.save(receita);
    }

    public ValoresNutricionais CalcularPreview(Receita receita){
        for(IngredienteReceita ing : receita.getIngredientes()){
            ItemTACO item = tacoService.buscarPorNome(ing.getNome());
            ing.setItemTACO(item); //@Transient não salva no banco 
        }
        return macroService.calcular(receita);
    }

}
