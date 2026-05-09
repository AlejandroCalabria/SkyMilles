package com.NexGen.nutriiftm.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "receita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeProduto;
    private double pesoTotalG;
    private double porcaoG;
    private String unidadeMedidaPorcao;
    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    //cascade = cascadeType.ALL, orphanRemoval = true oq significa: 
    // cascadeType.ALL significa que todas as operações (persistir, mesclar, remover, etc.) realizadas na entidade Receita serão propagadas para os 
    // ingredientes associados. orphanRemoval = true significa que se um ingrediente for removido da lista de ingredientes da receita, 
    // ele também será removido do banco de dados.
    private List<IngredienteReceita> ingredientes = new ArrayList<>();
}
