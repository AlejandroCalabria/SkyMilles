package modelo;

import java.util.ArrayList;

public class Elemento {
   private int eleCodigo;
   private String eleNome;
   private int eleOrdem;
   private double eleValorRecomendado;

    public int getEleOrdem() {
        return eleOrdem;
    }

    public void setEleOrdem(int eleOrdem) {
        this.eleOrdem = eleOrdem;
    }

    public double getEleValorRecomendado() {
        return eleValorRecomendado;
    }

    public void setEleValorRecomendado(double eleValorRecomendado) {
        this.eleValorRecomendado = eleValorRecomendado;
    }

    public int getEleCodigo() {
        return eleCodigo;
    }

    public void setEleCodigo(int eleCodigo) {
        this.eleCodigo = eleCodigo;
    }

    public String getEleNome() {
        return eleNome;
    }

    public void setEleNome(String eleNome) {
        this.eleNome = eleNome;
    }

    public Elemento() {
    }

    public Elemento(int eleCodigo, String eleNome, int eleOrdem, double eleValorRecomendado) {
        this.eleCodigo = eleCodigo;
        this.eleNome = eleNome;
        this.eleOrdem = eleOrdem;
        this.eleValorRecomendado = eleValorRecomendado;
    }

    @Override
    public String toString() {
        return "Elemento{" + "eleCodigo=" + eleCodigo + ", eleNome=" + eleNome + ", eleOrdem=" + eleOrdem + ", eleValorRecomendado=" + eleValorRecomendado + '}';
    }
    
    public ArrayList<Elemento> montarElemento(){
        ArrayList<Elemento> res = new ArrayList();
        Elemento ele = new Elemento(1, "Carboridratos (g)", 1, 300);  
        res.add(ele);
        ele = new Elemento(2, "Açúcares Totais (g)", 2, 0);  
        res.add(ele);
        ele = new Elemento(3, "Açúcares Adicionados (g)", 3, 50);  
        res.add(ele);
        ele = new Elemento(4, "Proteínas (g)", 4, 75);  
        res.add(ele);
        ele = new Elemento(5, "Gorduras Totais (g)", 5, 55);  
        res.add(ele);
        ele = new Elemento(6, "Gorduras Saturadas (g)", 6, 22);
        res.add(ele);
        ele = new Elemento(7, "Gorduras Trans (g)", 7, 0);
        res.add(ele);
        ele = new Elemento(8, "Fibras Alimentares (g)", 8, 25);
        res.add(ele);
        ele = new Elemento(9, "Sódio (mg)", 9, 2400);
        res.add(ele);
        ele = new Elemento(10, "Vitamina A (μg)", 10, 800);
        res.add(ele);
        ele = new Elemento(11, "Vitamina D (μg)", 11, 15);
        res.add(ele);
        ele = new Elemento(12, "Vitamina E (mg)", 12, 15);
        res.add(ele);
        ele = new Elemento(13, "Vitamina K (μg)", 13, 120);
        res.add(ele);
        ele = new Elemento(14, "Vitamina C (mg)", 14, 100);
        res.add(ele);
        ele = new Elemento(15, "Vitamina B6 (mg)", 15, 1.3);
        res.add(ele);
        ele = new Elemento(16, "Vitamina B12 (μg)", 16, 2.4);
        res.add(ele);
        ele = new Elemento(17, "Cálcio (mg)", 17, 1000);
        res.add(ele);
        ele = new Elemento(18, "Cobre (μg)", 18, 900);
        res.add(ele);
        ele = new Elemento(19, "Ferro (mg)", 19, 14);
        res.add(ele);
        ele = new Elemento(20, "Fósforo (mg)", 20, 700);
        res.add(ele);
        ele = new Elemento(21, "Iódo (μg)", 21, 150);
        res.add(ele);
        ele = new Elemento(22, "Magnésio (mg)", 22, 420);
        res.add(ele);
        ele = new Elemento(23, "Manganês (mg)", 23, 3);
        res.add(ele);
        ele = new Elemento(24, "Potássio (mg)", 24, 3500);
        res.add(ele);
        ele = new Elemento(25, "Selênio (μg)", 25, 60);
        res.add(ele);
        ele = new Elemento(26, "Zinco (mg)", 26, 11);
        res.add(ele);
        ele = new Elemento(27, "Colina (mg)", 27, 550);
        res.add(ele);
        return res;
    }
    
    public static void main(String[] args) {
        Elemento ele = new Elemento();
        
        ArrayList<Elemento> res = ele.montarElemento();
        for(Elemento e : res)
            e.toString();
    }
   
}
