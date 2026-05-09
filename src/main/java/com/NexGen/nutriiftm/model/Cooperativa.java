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
 * @autor ALEJANDRO CALABRIA O MELHOR BACK-ENDER DO BRASIL
 * :)
 */

@Getter
@Setter
@Entity
@Table(name = "cooperativa")
@NoArgsConstructor
@AllArgsConstructor
public class Cooperativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cooCodigo;

    private String cooNome;
    private String cooCNPJ;
    private String cooEndereco;
    private String cooCidade;

    @OneToMany(mappedBy = "cooperativa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fabricante> fabricantes = new ArrayList<>();
    /*
    public ArrayList<Cooperativa> montarCooperativa(){
        ArrayList<Cooperativa> res = new ArrayList<>();
        Cooperativa coo = new Cooperativa(1, "Cooper Safra", "00.000.0000/00", "Rua x", "Uberlândia");  
        res.add(coo);
        return res;
    }
    */
}
