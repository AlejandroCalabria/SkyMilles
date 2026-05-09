package com.NexGen.nutriiftm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fabricante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fabricante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fabCodigo;

    private String fabNome;
    private String fabEndereco;
    private String fabContato;

    @ManyToOne
    @JoinColumn(name = "cooCodigo")
    private Cooperativa cooperativa;
   
    /*public ArrayList<Fabricante> montarFabricante(){
        ArrayList<Fabricante> res = new ArrayList<>();
        Fabricante fab = new Fabricante(1, "Catarina Henrique de Moura", "Chácara Canta Gallo - Setor Douradinho", 1, "(34) 99116-1669)"); 
        res.add(fab);
        return res;
    }
        */
    /* 
    public List findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
   */
}
