package controle;

import conexao.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Elemento;
import modelo.TabNutElemento;
import modelo.TabelaNutricional;

/**
 *
 * @author ricbo
 */
public class TabNutElementoControle {
    
    private static double TAB_VALOR = 100;
    private static double TAB_CALORIAS = 2000;
    
    public ArrayList<TabNutElemento> consultarElementosNutricionais(int tabCodigo, double porcao){
        ArrayList<TabNutElemento> nuts = new ArrayList<>();
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        try {

            String consulta = "select * from tabnutelemento where tabCodigo = ?";
            stm = conn.prepareStatement(consulta);
            stm.setInt(1, tabCodigo);
            resultado = stm.executeQuery();

            while (resultado.next()) {
                TabNutElemento ele = new TabNutElemento();
                ele.setEleCodigo(resultado.getInt("eleCodigo"));
                ele.setTabCodigo(resultado.getInt("tabCodigo"));
                ele.setTneCodigo(resultado.getInt("tneCodigo"));
                ele.setTneValor(resultado.getDouble("tneValor"));
                // calculo automático dos valores dos nutrientes
                ElementoControle ec = new ElementoControle();
                Elemento e = ec.consultarElementoCodigo(ele.getEleCodigo());
                TabelaNutricionalControle tn = new TabelaNutricionalControle();

                ele.setTneValorPadrao(calcularValorPadrao(ele.getTneValor(), porcao));
                ele.setTneVD(calcularVD(ele.getTneValor(), e.getEleValorRecomendado()));

                nuts.add(ele);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TabNutElementoControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return nuts;
    }

    private double calcularValorPadrao(double v, double p){
        if(p != 0)
            return Math.round(v * TAB_VALOR / p * 10.0) / 10.0;
        else
            return 0;
    }
    
    private double calcularVD(double valor, double recomendado){
        if(recomendado != 0)
            return Math.round(valor / recomendado * 100 * 10.0) / 10.0;
        else
            return 0;
    }
    
    public String inserirElementosNutricionais(TabNutElemento ele) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "INSERT INTO tabnutelemento (tabCodigo, elecodigo, tneValor) VALUES (?,?,?)";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ele.getTabCodigo());
            stm.setInt(2, ele.getEleCodigo());
            stm.setDouble(3, ele.getTneValor());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = "erro";
        } 
        return resultado;
    }

    public TabNutElementoControle() {
    }
  
}
