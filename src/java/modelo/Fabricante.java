package modelo;

import java.util.ArrayList;

public class Fabricante {
   private int fabCodigo;
   private String fabNome;
   private String fabEndereco;
   private int fabCooperativa;
   private String fabContato;

    public String getFabContato() {
        return fabContato;
    }

    public void setFabContato(String fabContato) {
        this.fabContato = fabContato;
    }

    public int getFabCodigo() {
        return fabCodigo;
    }

    public void setFabCodigo(int fabCodigo) {
        this.fabCodigo = fabCodigo;
    }

    public String getFabNome() {
        return fabNome;
    }

    public void setFabNome(String fabNome) {
        this.fabNome = fabNome;
    }

    public String getFabEndereco() {
        return fabEndereco;
    }

    public void setFabEndereco(String fabEndereco) {
        this.fabEndereco = fabEndereco;
    }

    public int getFabCooperativa() {
        return fabCooperativa;
    }

    public void setFabCooperativa(int fabCooperativa) {
        this.fabCooperativa = fabCooperativa;
    }

    public Fabricante() {
    }

    public Fabricante(int fabCodigo, String fabNome, String fabEndereco, int fabCooperativa, String fabContato) {
        this.fabCodigo = fabCodigo;
        this.fabNome = fabNome;
        this.fabEndereco = fabEndereco;
        this.fabCooperativa = fabCooperativa;
        this.fabContato = fabContato;
    }
   
    public ArrayList<Fabricante> montarFabricante(){
        ArrayList<Fabricante> res = new ArrayList();
        Fabricante fab = new Fabricante(1, "Catarina Henrique de Moura", "Chácara Canta Gallo - Setor Douradinho", 1, "(34) 99116-1669)"); 
        res.add(fab);
        return res;
    }
   
}
