package com.NexGen.nutriiftm.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proCodigo;       // int → Long
    private String proNomeFantasia;
    private String proNome;
    // private int fabCodigo;     ← REMOVIDO, era isso que causava o erro
    private LocalDate proDataFabricacao;
    private LocalDate proDataVencimento;
    private double proPeso;
    private String proRecomendacoes;

    @Column(columnDefinition = "TEXT")
    private String proIngredientes;

    @ManyToOne
    @JoinColumn(name = "fabCodigo")
    private Fabricante fabricante;  // ← só isso, sem o int duplicado
}