package modelo;

import java.util.List;

/**
 * Representa uma sugestao de ingrediente quando a busca nao encontrou
 * correspondencia direta na TACO.
 */
public class SugestaoIngrediente {
    public String nomeBusca;
    public List<ItemTACO> sugestoes;

    public SugestaoIngrediente(String nomeBusca, List<ItemTACO> sugestoes) {
        this.nomeBusca = nomeBusca;
        this.sugestoes = sugestoes;
    }

    public SugestaoIngrediente(String nomeBusca, ItemTACO sugestao) {
        this.nomeBusca = nomeBusca;
        this.sugestoes = sugestao != null ? java.util.Arrays.asList(sugestao) : java.util.Collections.<ItemTACO>emptyList();
    }
}
