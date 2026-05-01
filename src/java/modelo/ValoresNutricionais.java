package modelo;

/**
 * Resultados nutricionais da Receita calculados para 100g/ml e por porção.
 * Inclui %VD (Valor Diário) conforme RDC 360/2003 da ANVISA.
 */
public class ValoresNutricionais {
    // Valores por 100g
    private double energia100g;
    private double carboidrato100g;
    private double proteina100g;
    private double lipideos100g;
    private double saturado100g;
    private double fibra100g;
    private double sodio100g;

    // Valores por porção
    private double energiaPorcao;
    private double carboidratoPorcao;
    private double proteinaPorcao;
    private double lipideosPorcao;
    private double saturadoPorcao;
    private double fibraPorcao;
    private double sodioPorcao;

    // % Valores Diários (base 2000 kcal)
    private double VDEnergia;
    private double VDCarboidrato;    // VD = 300g
    private double VDProteina;       // VD = 75g
    private double VDLipideos;       // VD = 55g
    private double VDSaturado;       // VD = 22g
    private double VDFibra;          // VD = 25g
    private double VDSodio;          // VD = 2400mg

    public ValoresNutricionais() {}

    public double getEnergia100g() { return energia100g; }
    public void setEnergia100g(double v) { this.energia100g = v; }
    public double getCarboidrato100g() { return carboidrato100g; }
    public void setCarboidrato100g(double v) { this.carboidrato100g = v; }
    public double getProteina100g() { return proteina100g; }
    public void setProteina100g(double v) { this.proteina100g = v; }
    public double getLipideos100g() { return lipideos100g; }
    public void setLipideos100g(double v) { this.lipideos100g = v; }
    public double getSaturado100g() { return saturado100g; }
    public void setSaturado100g(double v) { this.saturado100g = v; }
    public double getFibra100g() { return fibra100g; }
    public void setFibra100g(double v) { this.fibra100g = v; }
    public double getSodio100g() { return sodio100g; }
    public void setSodio100g(double v) { this.sodio100g = v; }

    public double getEnergiaPorcao() { return energiaPorcao; }
    public void setEnergiaPorcao(double v) { this.energiaPorcao = v; }
    public double getCarboidratoPorcao() { return carboidratoPorcao; }
    public void setCarboidratoPorcao(double v) { this.carboidratoPorcao = v; }
    public double getProteinaPorcao() { return proteinaPorcao; }
    public void setProteinaPorcao(double v) { this.proteinaPorcao = v; }
    public double getLipideosPorcao() { return lipideosPorcao; }
    public void setLipideosPorcao(double v) { this.lipideosPorcao = v; }
    public double getSaturadoPorcao() { return saturadoPorcao; }
    public void setSaturadoPorcao(double v) { this.saturadoPorcao = v; }
    public double getFibraPorcao() { return fibraPorcao; }
    public void setFibraPorcao(double v) { this.fibraPorcao = v; }
    public double getSodioPorcao() { return sodioPorcao; }
    public void setSodioPorcao(double v) { this.sodioPorcao = v; }

    public double getVDEnergia() { return VDEnergia; }
    public void setVDEnergia(double v) { this.VDEnergia = v; }
    public double getVDCarboidrato() { return VDCarboidrato; }
    public void setVDCarboidrato(double v) { this.VDCarboidrato = v; }
    public double getVDProteina() { return VDProteina; }
    public void setVDProteina(double v) { this.VDProteina = v; }
    public double getVDLipideos() { return VDLipideos; }
    public void setVDLipideos(double v) { this.VDLipideos = v; }
    public double getVDSaturado() { return VDSaturado; }
    public void setVDSaturado(double v) { this.VDSaturado = v; }
    public double getVDFibra() { return VDFibra; }
    public void setVDFibra(double v) { this.VDFibra = v; }
    public double getVDSodio() { return VDSodio; }
    public void setVDSodio(double v) { this.VDSodio = v; }
}
