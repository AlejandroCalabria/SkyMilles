package com.NexGen.nutriiftm.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa uma sugestao de ingrediente quando a busca nao encontrou
 * correspondencia direta na TACO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SugestaoIngrediente {
    public String nomeBusca;
    public List<ItemTACO> sugestoes;
}
