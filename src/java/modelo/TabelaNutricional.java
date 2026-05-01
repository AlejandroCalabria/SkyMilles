package modelo;

import java.util.ArrayList;


public class TabelaNutricional {
    private ArrayList<TabNutElemento> tneElementos = new ArrayList<>();
    private int tabCodigo;
    private int undCodigo;
    private double tabValorEnergetico;
    private double tabVD;
    private double tabPorcao;
    private double tabPorcaoPadrao;
    private int proCodigo;
    private double tabTotalPorcao;
    private double tabValorEnergeticoPorcao;
    private int tabUnidadeMedidasColheres;
    private int tabTotalColheres;

    public int getTabUnidadeMedidasColheres() {
        return tabUnidadeMedidasColheres;
    }

    public void setTabUnidadeMedidasColheres(int tabUnidadeMedidasColheres) {
        this.tabUnidadeMedidasColheres = tabUnidadeMedidasColheres;
    }

    public int getTabTotalColheres() {
        return tabTotalColheres;
    }

    public void setTabTotalColheres(int tabTotalColheres) {
        this.tabTotalColheres = tabTotalColheres;
    }
    
    public double getTabValorEnergeticoPorcao() {
        return tabValorEnergeticoPorcao;
    }

    public void setTabValorEnergeticoPorcao(double tabValorEnergeticoPorcao) {
        this.tabValorEnergeticoPorcao = tabValorEnergeticoPorcao;
    }

    public double getTabPorcaoPadrao() {
        return tabPorcaoPadrao;
    }

    public void setTabPorcaoPadrao(double tabPorcaoPadrao) {
        this.tabPorcaoPadrao = tabPorcaoPadrao;
    }
    

    public double getTabTotalPorcao() {
        return tabTotalPorcao;
    }

    public void setTabTotalPorcao(double tabTotalPorcao) {
        this.tabTotalPorcao = tabTotalPorcao;
    }

    public int getUndCodigo() {
        return undCodigo;
    }

    public void setUndCodigo(int undCodigo) {
        this.undCodigo = undCodigo;
    }

    public ArrayList<TabNutElemento> getTneElementos() {
        return tneElementos;
    }

    public void setTneElementos(ArrayList<TabNutElemento> tneElementos) {
        this.tneElementos = tneElementos;
    }

    public int getTabCodigo() {
        return tabCodigo;
    }

    public void setTabCodigo(int tabCodigo) {
        this.tabCodigo = tabCodigo;
    }

    public double getTabValorEnergetico() {
        return tabValorEnergetico;
    }

    public void setTabValorEnergetico(double tabValorEnergetico) {
        this.tabValorEnergetico = tabValorEnergetico;
    }

    public double getTabVD() {
        return tabVD;
    }

    public void setTabVD(double tabVD) {
        this.tabVD = tabVD;
    }

    public double getTabPorcao() {
        return tabPorcao;
    }

    public void setTabPorcao(double tabPorcao) {
        this.tabPorcao = tabPorcao;
    }

    public int getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(int proCodigo) {
        this.proCodigo = proCodigo;
    }

    public TabelaNutricional() {
    }

    public TabelaNutricional(int tabCodigo, double tabValorEnergetico, double tabValorEnergeticoPorcao, double tabVD, double tabPorcao, double tabPorcaoPadrao, int proCodigo, int undCodigo, double tabTotalPorcao, int tabUnidadeMedidasColheres, int tabTotalColheres) {
        this.tabCodigo = tabCodigo;
        this.tabValorEnergetico = tabValorEnergetico;
        this.tabVD = tabVD;
        this.tabPorcao = tabPorcao;
        this.proCodigo = proCodigo;
        this.undCodigo = undCodigo;
        this.tabPorcaoPadrao = tabPorcaoPadrao;
        this.tabTotalPorcao = tabTotalPorcao;
        this.tabValorEnergeticoPorcao = tabValorEnergeticoPorcao;
        this.tabTotalColheres = tabTotalColheres;
        this.tabUnidadeMedidasColheres = tabUnidadeMedidasColheres;
    }

   
}
