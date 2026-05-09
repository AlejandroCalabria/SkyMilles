package com.NexGen.nutriiftm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ingrediente_receita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredienteReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double quantidadeG;
    private Integer codigoTACO;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;

    @Transient
    private ItemTACO itemTACO;
}