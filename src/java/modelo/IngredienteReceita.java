package modelo;

/**
 * Representa um ingrediente de uma receita com sua quantidade.
 */
public class IngredienteReceita {
    private String nome;
    private double quantidadeG;
    private ItemTACO itemTACO;

    public IngredienteReceita() {}

    public IngredienteReceita(String nome, double quantidadeG) {
        this.nome = nome;
        this.quantidadeG = quantidadeG;
    }

    public IngredienteReceita(String nome, double quantidadeG, ItemTACO itemTACO) {
        this.nome = nome;
        this.quantidadeG = quantidadeG;
        this.itemTACO = itemTACO;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getQuantidadeG() { return quantidadeG; }
    public void setQuantidadeG(double quantidadeG) { this.quantidadeG = quantidadeG; }
    public ItemTACO getItemTACO() { return itemTACO; }
    public void setItemTACO(ItemTACO itemTACO) { this.itemTACO = itemTACO; }
}
