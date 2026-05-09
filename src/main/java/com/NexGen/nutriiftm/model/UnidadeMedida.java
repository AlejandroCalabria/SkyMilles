/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NexGen.nutriiftm.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 *
 * @author ricbo
 * @author ALEJANDRO CALABRIA O MELHOR BACK-ENDER DO BRASIL
 * :)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unidademedida")
@Entity
public class UnidadeMedida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long undCodigo;
    private String undNome;

    @OneToMany(mappedBy = "unidadeMedida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TabelaNutricional> tabelasNutricionais = new ArrayList<>();
    
    /* 
    public ArrayList<UnidadeMedida> montarUnidadeMedida(){
        ArrayList<UnidadeMedida> res = new ArrayList<>();
        UnidadeMedida un = new UnidadeMedida(1, "ml");
        res.add(un);
        un = new UnidadeMedida(2, "mg");
        res.add(un);
        un = new UnidadeMedida(3, "colher de sobremesa");
        res.add(un);
        un = new UnidadeMedida(4, "colher de sopa");
        res.add(un);
        un = new UnidadeMedida(5, "copo");
        res.add(un);
        un = new UnidadeMedida(6, "g");
        res.add(un);
        un = new UnidadeMedida(7, "kg");
        res.add(un);
        return res;
    }
    */
}
