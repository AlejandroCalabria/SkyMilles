package modelo;

/**
 * Representa um item da Tabela TACO (Tabela Brasileira de Composição de Alimentos).
 * Valores nutricionais por 100g do alimento.
 */
public class ItemTACO {
    private int codigo;
    private String descricao;
    private String categoria;
    private double umidade;
    private double energia;
    private double proteina;
    private double lipideos;
    private double colesterol;
    private double carboidrato;
    private double fibra;
    private double cinzas;
    private double calcio;
    private double magnesio;
    private double manganes;
    private double fosforo;
    private double ferro;
    private double sodio;
    private double potassio;
    private double cobre;
    private double zinco;
    private double retinol;
    private double vitamB1;
    private double vitamB2;
    private double vitamB6;
    private double vitamB12;
    private double vitamC;
    private double vitamD;
    private double vitamE;
    private double acidoGraxoSaturado;
    private double acidoGraxoMonoinsaturado;
    private double acidoGraxoPoliinsaturado;

    public ItemTACO() {}

    public ItemTACO(int codigo, String descricao, String categoria) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getUmidade() { return umidade; }
    public void setUmidade(double umidade) { this.umidade = umidade; }
    public double getEnergia() { return energia; }
    public void setEnergia(double energia) { this.energia = energia; }
    public double getProteina() { return proteina; }
    public void setProteina(double proteina) { this.proteina = proteina; }
    public double getLipideos() { return lipideos; }
    public void setLipideos(double lipideos) { this.lipideos = lipideos; }
    public double getColesterol() { return colesterol; }
    public void setColesterol(double colesterol) { this.colesterol = colesterol; }
    public double getCarboidrato() { return carboidrato; }
    public void setCarboidrato(double carboidrato) { this.carboidrato = carboidrato; }
    public double getFibra() { return fibra; }
    public void setFibra(double fibra) { this.fibra = fibra; }
    public double getCinzas() { return cinzas; }
    public void setCinzas(double cinzas) { this.cinzas = cinzas; }
    public double getCalcio() { return calcio; }
    public void setCalcio(double calcio) { this.calcio = calcio; }
    public double getMagnesio() { return magnesio; }
    public void setMagnesio(double magnesio) { this.magnesio = magnesio; }
    public double getManganes() { return manganes; }
    public void setManganes(double manganes) { this.manganes = manganes; }
    public double getFosforo() { return fosforo; }
    public void setFosforo(double fosforo) { this.fosforo = fosforo; }
    public double getFerro() { return ferro; }
    public void setFerro(double ferro) { this.ferro = ferro; }
    public double getSodio() { return sodio; }
    public void setSodio(double sodio) { this.sodio = sodio; }
    public double getPotassio() { return potassio; }
    public void setPotassio(double potassio) { this.potassio = potassio; }
    public double getCobre() { return cobre; }
    public void setCobre(double cobre) { this.cobre = cobre; }
    public double getZinco() { return zinco; }
    public void setZinco(double zinco) { this.zinco = zinco; }
    public double getRetinol() { return retinol; }
    public void setRetinol(double retinol) { this.retinol = retinol; }
    public double getVitamB1() { return vitamB1; }
    public void setVitamB1(double vitamB1) { this.vitamB1 = vitamB1; }
    public double getVitamB2() { return vitamB2; }
    public void setVitamB2(double vitamB2) { this.vitamB2 = vitamB2; }
    public double getVitamB6() { return vitamB6; }
    public void setVitamB6(double vitamB6) { this.vitamB6 = vitamB6; }
    public double getVitamB12() { return vitamB12; }
    public void setVitamB12(double vitamB12) { this.vitamB12 = vitamB12; }
    public double getVitamC() { return vitamC; }
    public void setVitamC(double vitamC) { this.vitamC = vitamC; }
    public double getVitamD() { return vitamD; }
    public void setVitamD(double vitamD) { this.vitamD = vitamD; }
    public double getVitamE() { return vitamE; }
    public void setVitamE(double vitamE) { this.vitamE = vitamE; }
    public double getAcidoGraxoSaturado() { return acidoGraxoSaturado; }
    public void setAcidoGraxoSaturado(double v) { this.acidoGraxoSaturado = v; }
    public double getAcidoGraxoMonoinsaturado() { return acidoGraxoMonoinsaturado; }
    public void setAcidoGraxoMonoinsaturado(double v) { this.acidoGraxoMonoinsaturado = v; }
    public double getAcidoGraxoPoliinsaturado() { return acidoGraxoPoliinsaturado; }
    public void setAcidoGraxoPoliinsaturado(double v) { this.acidoGraxoPoliinsaturado = v; }
}
