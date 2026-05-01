/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author ricbo
 */
public class UnidadeMedida {
    private int undCodigo;
    private String undNome;

    public UnidadeMedida() {
    }

    public UnidadeMedida(int undCodigo, String undNome) {
        this.undCodigo = undCodigo;
        this.undNome = undNome;
    }

    public int getUndCodigo() {
        return undCodigo;
    }

    public void setUndCodigo(int undCodigo) {
        this.undCodigo = undCodigo;
    }

    public String getUndNome() {
        return undNome;
    }

    public void setUndNome(String undNome) {
        this.undNome = undNome;
    }
    
    public ArrayList<UnidadeMedida> montarUnidadeMedida(){
        ArrayList<UnidadeMedida> res = new ArrayList();
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
}
