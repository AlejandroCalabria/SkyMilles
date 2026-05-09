package com.NexGen.nutriiftm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * Representa um item da Tabela TACO.
 * Lido do arquivo JSON — NÃO é uma tabela do banco.
 * 100g
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}