package com.NexGen.nutriiftm.domain.nutricional;

/**
 * Constantes nutricionais conforme IN 75/2020 (ANVISA).
 * SINGLE SOURCE OF TRUTH — nunca duplicar esses valores em outros arquivos.
 *
 * Referência: Instrução Normativa 75, de 8 de outubro de 2020.
 */
public final class NutricionalConstants {

    private NutricionalConstants() {}

    // ── Fator de conversão ─────────────────────────────────────────────────
    /** 1 kcal = 4,184 kJ (fator ANVISA oficial, IN 75/2020) */
    public static final double KCAL_TO_KJ = 4.184;

    // ── Fatores de Atwater para cálculo de energia (IN 75/2020) ──────────
    /** Proteína: 4 kcal/g */
    public static final double KCAL_POR_GRAMA_PROTEINA  = 4.0;
    /** Carboidrato: 4 kcal/g */
    public static final double KCAL_POR_GRAMA_CARBOIDRATO = 4.0;
    /** Lipídeo: 9 kcal/g */
    public static final double KCAL_POR_GRAMA_LIPIDEO   = 9.0;
    /**
     * ATENÇÃO: A fibra NÃO entra no cálculo de energia declarada no rótulo
     * (IN 75/2020, Anexo II). Constante mantida para documentação explícita.
     */
    public static final double KCAL_POR_GRAMA_FIBRA_ROTULO = 0.0;

    // ── Valores Diários de Referência (VDR) — IN 75/2020, dieta 2000 kcal ─
    public static final double VD_ENERGIA_KCAL  = 2000.0;
    public static final double VD_CARBOIDRATO_G  = 300.0;
    public static final double VD_PROTEINA_G     = 75.0;
    public static final double VD_LIPIDEOS_G     = 55.0;
    public static final double VD_SATURADO_G     = 22.0;
    public static final double VD_FIBRA_G        = 25.0;
    public static final double VD_SODIO_MG       = 2400.0;

    // ── Regras de declaração (IN 75/2020) ─────────────────────────────────
    /**
     * Gorduras trans < 0,2g por porção → declarar como 0 no rótulo.
     */
    public static final double TRANS_LIMIAR_ZERO_G = 0.2;

    // ── Limites de segurança de UI ─────────────────────────────────────────
    public static final int MAX_PORCOES_EMBALAGEM = 9999;
    public static final int MIN_PORCOES_EMBALAGEM = 1;
}