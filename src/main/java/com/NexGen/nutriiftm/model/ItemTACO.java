package com.NexGen.nutriiftm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * Representa um item da Tabela TACO (novo formato TACO.json).
 * Lido do arquivo JSON — NÃO é uma entidade do banco.
 * Valores por 100g. "NA", "Tr" e "" são tratados como 0.0 no parser.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemTACO {

    // ── Identificação ────────────────────────────────────────────────────────
    private int    id;           // novo: era String codigo
    private String codigo;       // mantido para compatibilidade (preenchido com String.valueOf(id))
    private String descricao;    // ← getter getDescricao() preservado
    private String categoria;    // ← getter getCategoria() preservado

    // ── Macronutrientes (getters preservados) ────────────────────────────────
    private double umidade;       // humidity_percents
    private double energia;       // energy_kcal      ← getEnergia()
    private double energiaKj;     // energy_kcal * 4.184 (calculado no parser)
    private double proteina;      // protein_g        ← getProteina()
    private double lipideos;      // lipid_g          ← getLipideos()
    private double colesterol;    // cholesterol_mg
    private double carboidrato;   // carbohydrate_g   ← getCarboidrato()
    private double acucarTotal;   // (não existe no novo JSON → 0.0)
    private double fibra;         // fiber_g          ← getFibra()
    private double sodio;         // sodium_mg        ← getSodio()

    // ── Micronutrientes ──────────────────────────────────────────────────────
    private double calcio;        // calcium_mg
    private double magnesio;      // magnesium_mg
    private double manganes;      // manganese_mg
    private double fosforo;       // phosphorus_mg
    private double ferro;         // iron_mg
    private double potassio;      // potassium_mg
    private double cobre;         // copper_mg
    private double zinco;         // zinc_mg

    // ── Vitaminas ────────────────────────────────────────────────────────────
    private double retinol;       // retinol_mcg
    private double vitamB1;       // thiamine_mg
    private double vitamB2;       // riboflavin_mg
    private double vitamB6;       // pyridoxine_mg
    private double vitamB12;      // cobalamin_mcg
    private double vitamC;        // vitaminC_mg
    private double vitamD;        // vitaminD_mcg (se existir)
    private double vitamE;        // vitaminE_mg  (se existir)

    // ── Ácidos graxos ────────────────────────────────────────────────────────
    private double acidoGraxoSaturado;        // saturated_g   ← getAcidoGraxoSaturado()
    private double acidoGraxoMonoinsaturado;  // monounsaturated_g
    private double acidoGraxoPoliinsaturado;  // polyunsaturated_g
    private double cinzas;                    // ash_g
}