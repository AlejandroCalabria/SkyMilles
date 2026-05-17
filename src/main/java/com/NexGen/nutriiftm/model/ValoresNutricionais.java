package com.NexGen.nutriiftm.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Resultado imutável do cálculo nutricional.
 *
 * Tipos:
 *  - energia/kJ: int (kcal e kJ são inteiros no rótulo ANVISA)
 *  - macros em g: double com 1 casa decimal
 *  - sodio: int (mg, inteiro)
 *  - %VD: int (sem casas decimais)
 *
 * Construído via Builder — nunca use setters ad-hoc.
 */
@Getter
@Builder
public class ValoresNutricionais {

    // ── Por 100 g ────────────────────────────────────────────────────────────
    private final int    energia100kcal;
    private final int    energia100kj;
    private final double carboidrato100g;
    private final double acucaresTotal100g;
    private final double proteina100g;
    private final double lipideos100g;
    private final double saturado100g;
    private final double trans100g;          // 0 se < 0,2g (IN 75/2020)
    private final double fibra100g;
    private final int    sodio100mg;

    // ── Por porção ────────────────────────────────────────────────────────────
    private final int    energiaPorcaoKcal;
    private final int    energiaPorcaoKj;
    private final double carboidratoPorcao;
    private final double acucaresTotalPorcao;
    private final double proteinaPorcao;
    private final double lipideosPorcao;
    private final double saturadoPorcao;
    private final double transPorcao;
    private final double fibraPorcao;
    private final int    sodioPorcaoMg;

    // ── %VD (inteiros, sem casas decimais) ────────────────────────────────────
    private final int vdEnergia;
    private final int vdCarboidrato;
    private final int vdProteina;
    private final int vdLipideos;
    private final int vdSaturado;
    // Trans: VD não estabelecido — sem campo
    private final int vdFibra;
    private final int vdSodio;

    // ── Auxiliar ──────────────────────────────────────────────────────────────
    private final double pesoTotalG;

    /**
     * Retorna true quando nenhum ingrediente foi encontrado na TBCA.
     */
    public boolean isEmpty() {
        return pesoTotalG <= 0;
    }

    // ── Compatibilidade legada (getters com nomes antigos) ────────────────────
    // Mantidos para não quebrar templates Thymeleaf existentes.
    // Remover após atualização dos templates.

    /** @deprecated use {@link #getEnergia100kcal()} */
    @Deprecated public int getEnergia100g()          { return energia100kcal; }
    /** @deprecated use {@link #getEnergiaKj100g()} */
    @Deprecated public int getEnergiaKj100g()        { return energia100kj; }
    /** @deprecated use {@link #getEnergiaPorcaoKcal()} */
    @Deprecated public int getEnergiaPorcao()        { return energiaPorcaoKcal; }
    /** @deprecated use {@link #getEnergiaKjPorcao()} */
    @Deprecated public int getEnergiaKjPorcao()      { return energiaPorcaoKj; }
    /** @deprecated use {@link #getSodio100mg()} */
    @Deprecated public int getSodio100g()            { return sodio100mg; }
    /** @deprecated use {@link #getSodioPorcaoMg()} */
    @Deprecated public int getSodioPorcao()          { return sodioPorcaoMg; }
    /** @deprecated use {@link #getVdEnergia()} */
    @Deprecated public int getVDEnergia()            { return vdEnergia; }
    /** @deprecated use {@link #getVdCarboidrato()} */
    @Deprecated public int getVDCarboidrato()        { return vdCarboidrato; }
    /** @deprecated use {@link #getVdProteina()} */
    @Deprecated public int getVDProteina()           { return vdProteina; }
    /** @deprecated use {@link #getVdLipideos()} */
    @Deprecated public int getVDLipideos()           { return vdLipideos; }
    /** @deprecated use {@link #getVdSaturado()} */
    @Deprecated public int getVDSaturado()           { return vdSaturado; }
    /** @deprecated use {@link #getVdFibra()} */
    @Deprecated public int getVDFibra()              { return vdFibra; }
    /** @deprecated use {@link #getVdSodio()} */
    @Deprecated public int getVDSodio()              { return vdSodio; }
    /** @deprecated use {@link #getAcucaresTotal100g()} */
    @Deprecated public double getAcucaresAdicionados100g()   { return 0.0; }
    /** @deprecated use {@link #getAcucaresTotalPorcao()} */
    @Deprecated public double getAcucaresAdicionadosPorcao() { return 0.0; }
}