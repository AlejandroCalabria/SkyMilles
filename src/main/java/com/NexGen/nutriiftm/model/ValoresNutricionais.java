package com.NexGen.nutriiftm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Resultados nutricionais da Receita calculados para 100g/ml e por porção.
 * Inclui %VD (Valor Diário) conforme RDC 360/2003 da ANVISA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
