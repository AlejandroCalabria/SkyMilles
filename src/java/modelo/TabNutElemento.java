package modelo;

public class TabNutElemento {
    private int tneCodigo;
    private int tabCodigo;
    private int eleCodigo;
    private double tneValor;
    private double tneValorPadrao;
    private double tneVD;

    public int getTneCodigo() {
        return tneCodigo;
    }

    public void setTneCodigo(int tneCodigo) {
        this.tneCodigo = tneCodigo;
    }

    public int getTabCodigo() {
        return tabCodigo;
    }

    public void setTabCodigo(int tabCodigo) {
        this.tabCodigo = tabCodigo;
    }

    public int getEleCodigo() {
        return eleCodigo;
    }

    public void setEleCodigo(int eleCodigo) {
        this.eleCodigo = eleCodigo;
    }

    public double getTneValor() {
        return tneValor;
    }

    public void setTneValor(double tneValor) {
        this.tneValor = tneValor;
    }

    public double getTneVD() {
        return tneVD;
    }

    public void setTneVD(double tneVD) {
        this.tneVD = tneVD;
    }

    public TabNutElemento() {
    }

    public double getTneValorPadrao() {
        return tneValorPadrao;
    }

    public void setTneValorPadrao(double tneValorPadrao) {
        this.tneValorPadrao = tneValorPadrao;
    }

    public TabNutElemento(int tneCodigo, int tabCodigo, int eleCodigo, double tneValor, double tneValorPadrao, double tneVD) {
        this.tneCodigo = tneCodigo;
        this.tabCodigo = tabCodigo;
        this.eleCodigo = eleCodigo;
        this.tneValor = tneValor;
        this.tneValorPadrao = tneValorPadrao;
        this.tneVD = tneVD;
    }

    
}
