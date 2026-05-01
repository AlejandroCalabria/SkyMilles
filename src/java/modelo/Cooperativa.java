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
public class Cooperativa {
    private int cooCodigo;
    private String cooNome;
    private String cooCNPJ;
    private String cooEndereco;
    private String cooCidade;

    public int getCooCodigo() {
        return cooCodigo;
    }

    public void setCooCodigo(int cooCodigo) {
        this.cooCodigo = cooCodigo;
    }

    public String getCooNome() {
        return cooNome;
    }

    public void setCooNome(String cooNome) {
        this.cooNome = cooNome;
    }

    public String getCooCNPJ() {
        return cooCNPJ;
    }

    public void setCooCNPJ(String cooCNPJ) {
        this.cooCNPJ = cooCNPJ;
    }

    public String getCooEndereco() {
        return cooEndereco;
    }

    public void setCooEndereco(String cooEndereco) {
        this.cooEndereco = cooEndereco;
    }

    public String getCooCidade() {
        return cooCidade;
    }

    public void setCooCidade(String cooCidade) {
        this.cooCidade = cooCidade;
    }

    public Cooperativa() {
    }

    public Cooperativa(int cooCodigo, String cooNome, String cooCNPJ, String cooEndereco, String cooCidade) {
        this.cooCodigo = cooCodigo;
        this.cooNome = cooNome;
        this.cooCNPJ = cooCNPJ;
        this.cooEndereco = cooEndereco;
        this.cooCidade = cooCidade;
    }
    
    public ArrayList<Cooperativa> montarCooperativa(){
        ArrayList<Cooperativa> res = new ArrayList();
        Cooperativa coo = new Cooperativa(1, "Cooper Safra", "00.000.0000/00", "Rua x", "Uberlândia");  
        res.add(coo);
        return res;
    }
}
