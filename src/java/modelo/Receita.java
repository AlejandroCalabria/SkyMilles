package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma receita (receita final do produto) com lista de ingredientes
 * e valores nutricionais calculados a partir da TACO.
 */
public class Receita {
    private String nomeProduto;
    private double pesoTotalG;
    private double porcaoG;
    private String unidadeMedidaPorcao;
    private List<IngredienteReceita> ingredientes = new ArrayList<>();

    public Receita() {}

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public double getPesoTotalG() { return pesoTotalG; }
    public void setPesoTotalG(double pesoTotalG) { this.pesoTotalG = pesoTotalG; }
    public double getPorcaoG() { return porcaoG; }
    public void setPorcaoG(double porcaoG) { this.porcaoG = porcaoG; }
    public String getUnidadeMedidaPorcao() { return unidadeMedidaPorcao; }
    public void setUnidadeMedidaPorcao(String unidadeMedidaPorcao) { this.unidadeMedidaPorcao = unidadeMedidaPorcao; }
    public List<IngredienteReceita> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<IngredienteReceita> ingredientes) { this.ingredientes = ingredientes; }

    public void adicionarIngrediente(IngredienteReceita ing) {
        this.ingredientes.add(ing);
        this.pesoTotalG += ing.getQuantidadeG();
    }

    public int getTotalPorcoes() {
        if (porcaoG <= 0) return 0;
        return (int) Math.round(pesoTotalG / porcaoG);
    }
}
