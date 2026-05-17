package com.NexGen.nutriiftm.domain.nutricional;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Regras de arredondamento conforme IN 75/2020 (ANVISA).
 * Todos os arredondamentos nutricionais DEVEM passar por aqui.
 *
 * Regras:
 *  - Energia (kcal, kJ): arredondar para inteiro mais próximo.
 *  - Macronutrientes em gramas: 1 casa decimal (múltiplo de 0,1g).
 *  - Sódio em mg: inteiro.
 *  - %VD: inteiro (sem casas decimais).
 *  - Gorduras trans < 0,2g → declarar 0.
 */
public final class ArredondamentoAnvisa {

    private ArredondamentoAnvisa() {}

    /**
     * Energia (kcal ou kJ): inteiro mais próximo.
     * Retorna 0 para NaN/Inf — nunca propaga valores inválidos.
     */
    public static int energia(double valor) {
        if (!Double.isFinite(valor)) return 0;
        return (int) Math.round(valor);
    }

    /**
     * Macronutriente em gramas: 1 casa decimal, HALF_UP.
     */
    public static double gramas(double valor) {
        if (!Double.isFinite(valor)) return 0.0;
        return BigDecimal.valueOf(valor)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Sódio em mg: inteiro mais próximo.
     */
    public static int miligramas(double valor) {
        if (!Double.isFinite(valor)) return 0;
        return (int) Math.round(valor);
    }

    /**
     * %VD: inteiro, sem casas decimais.
     * Retorna 0 se o VD base for inválido (≤0).
     */
    public static int percentualVD(double valorPorcao, double vdBase) {
        if (!Double.isFinite(valorPorcao) || vdBase <= 0) return 0;
        double vd = valorPorcao / vdBase * 100.0;
        if (!Double.isFinite(vd)) return 0;
        return (int) Math.round(vd);
    }

    /**
     * Gorduras trans: se < 0,2g, declara 0 (IN 75/2020).
     */
    public static double gorduraTrans(double valor) {
        if (!Double.isFinite(valor) || valor < NutricionalConstants.TRANS_LIMIAR_ZERO_G) return 0.0;
        return gramas(valor);
    }

    /**
     * Converte valor por porção para valor por 100g.
     * Retorna 0.0 se porção for zero (evita divisão por zero).
     */
    public static double valorPor100g(double valorPorcao, double porcaoG) {
        if (porcaoG <= 0) return 0.0;
        return gramas(valorPorcao * 100.0 / porcaoG);
    }
}