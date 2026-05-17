package com.NexGen.nutriiftm.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Correções:
 *  - getTabPorcao() implementado corretamente (antes lançava UnsupportedOperationException).
 *  - Coluna mapeada na tabela correta (tab_nut_elemento, conforme nova arquitetura).
 */
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

    /** Valor por porção */
    private double tneValor;

    /** Valor por 100g (calculado via ArredondamentoAnvisa.valorPor100g) */
    private double tneValorPadrao;

    /** %VD da porção (calculado via ArredondamentoAnvisa.percentualVD) */
    private double tneVD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eleCodigo")
    private Elemento elemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tabCodigo")
    private TabelaNutricional tabelaNutricional;

    /**
     * CORREÇÃO #12: antes lançava UnsupportedOperationException.
     * Delegação limpa para a tabela associada.
     */
    public double getTabPorcao() {
        return tabelaNutricional != null ? tabelaNutricional.getTabPorcao() : 0.0;
    }
}