
package modelo;

import java.util.ArrayList;


public class Produto {
    private int proCodigo;
    private String proNome;
    private int fabCodigo;
    private String proDataFabricacao;
    private String proDataVencimento;
    private double proPeso;
    private String proRecomendacoes;
    private String proIngredientes;
    private String proNomeFantasia;

    public String getProRecomendacoes() {
        return proRecomendacoes;
    }

    public void setProRecomendacoes(String proRecomendacoes) {
        this.proRecomendacoes = proRecomendacoes;
    }

    public String getProIngredientes() {
        return proIngredientes;
    }

    public void setProIngredientes(String proIngredientes) {
        this.proIngredientes = proIngredientes;
    }

    public String getProNomeFantasia() {
        return proNomeFantasia;
    }

    public void setProNomeFantasia(String proNomeFantasia) {
        this.proNomeFantasia = proNomeFantasia;
    }

    public int getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(int proCodigo) {
        this.proCodigo = proCodigo;
    }

    public String getProNome() {
        return proNome;
    }

    public void setProNome(String proNome) {
        this.proNome = proNome;
    }

    public int getFabCodigo() {
        return fabCodigo;
    }

    public void setFabCodigo(int fabCodigo) {
        this.fabCodigo = fabCodigo;
    }

    public String getProDataFabricacao() {
        return proDataFabricacao;
    }

    public void setProDataFabricacao(String proDataFabricacao) {
        this.proDataFabricacao = proDataFabricacao;
    }

    public String getProDataVencimento() {
        return proDataVencimento;
    }

    public void setProDataVencimento(String proDataVencimento) {
        this.proDataVencimento = proDataVencimento;
    }

    public double getProPeso() {
        return proPeso;
    }

    public void setProPeso(double proPeso) {
        this.proPeso = proPeso;
    }

    public Produto() {
    }

    public Produto(int proCodigo, String proNome, int fabCodigo, String proDataFabricacao, String proDataVencimento, double proPeso, String proRecomendacoes, String proIngredientes, String proNomeFantasia) {
        this.proCodigo = proCodigo;
        this.proNome = proNome;
        this.fabCodigo = fabCodigo;
        this.proDataFabricacao = proDataFabricacao;
        this.proDataVencimento = proDataVencimento;
        this.proPeso = proPeso;
        this.proRecomendacoes = proRecomendacoes;
        this.proIngredientes = proIngredientes;
        this.proNomeFantasia = proNomeFantasia;
    }
    
    public ArrayList<Produto> montarProduto(){
        ArrayList<Produto> res = new ArrayList();
        Produto pro = new Produto(1, "MOLHO DE PIMENTA SUAVE", 
                                  1, "2024-11-01", 
                                  "2024-11-01", 150, 
                                  "Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias", 
                                  "Pimenta dedo de moça (sem sementes), Tomate Cereja, Óleo de soja, Sal",
                                  "Pimenta da Catarina");
        res.add(pro);
        pro = new Produto(2, "MOLHO DE PIMENTA FORTE", 
                          1, "2024-11-01", 
                          "2024-11-01", 150, 
                          "Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias", 
                          "Pimenta dedo de moça (com sementes), Tomate Cereja, Óleo de soja, Sal",
                          "Pimenta da Catarina");
        res.add(pro);
        pro = new Produto(3, "MOLHO DE PIMENTA FORTE", 
                          1, "2024-11-01", 
                          "2024-11-01", 150, 
                          "Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias", 
                          "Pimenta dedo de moça (com sementes), Tomate Cereja, Óleo de soja, Sal",
                          "Pimenta da Catarina");
        res.add(pro);
        pro = new Produto(4, "MOLHO DE PIMENTA BLEND DE PIMENTAS EXTRA FORTE", 
                          1, "2024-11-01", 
                          "2024-11-01", 150, 
                          "Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias", 
                          "Pimenta Malagueta, Pimenta dedo de moça (com sementes), Pimenta Chocolate Habanero, Pimenta Roxa, Tomate Cereja, Óleo de soja, Sal",
                          "Pimenta da Catarina");
        res.add(pro);
        pro = new Produto(5, "CONSERVA DE JURUBEBA", 
                          1, "2024-11-01", 
                          "2024-11-01", 150, 
                          "Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias", 
                          "Jurubeba (Solanum paniculatum), Vinagre, Sal, Alho.",
                          "Pimenta da Catarina");
        res.add(pro);
        return res;
    }
}
