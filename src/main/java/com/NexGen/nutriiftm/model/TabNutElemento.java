package com.NexGen.nutriiftm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tab_nut_elemento")
@Entity
public class TabNutElemento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tneCodigo;
    private double tneValor;
    private double tneValorPadrao;
    private double tneVD;


    @ManyToOne
    @JoinColumn(name = "eleCodigo")
    private Elemento elemento;

    @ManyToOne
    @JoinColumn(name = "tabCodigo")
    private TabelaNutricional tabelaNutricional;

    public double getTabPorcao() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTabPorcao'");
    }
}
