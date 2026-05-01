package controle;

import java.sql.*;
import java.util.ArrayList;
import conexao.ConexaoMySQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Elemento;

public class ElementoControle {
    public static void main(String[] args) {
        ElementoControle teste = new ElementoControle();
        //Elemento ins = new Elemento(1, "xxx", 26, 500);
        Elemento ins = new Elemento();
        
        //String res = teste.inserirElemento(ins);
        //System.out.println("Retorno: " + res);

        ArrayList<Elemento> ele = ins.montarElemento();
        teste.imprimirElementos(ele);
    }
    
    public Elemento consultarElementoCodigo(int eleCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        Elemento ele = new Elemento();
        try {
          String consulta = "select * from elemento where eleCodigo = ?";

          stm = conn.prepareStatement(consulta);
          stm.setInt(1, eleCodigo);
          resultado = stm.executeQuery();

          while(resultado.next()) {
            ele.setEleCodigo(resultado.getInt("eleCodigo"));
            ele.setEleNome(resultado.getString("eleNome"));
            ele.setEleOrdem(resultado.getInt("eleOrdem"));
            ele.setEleValorRecomendado(resultado.getDouble("eleValorRecomendado"));
          }
        } catch (SQLException ex) {
          System.out.println("Não conseguiu consultar os dados dos elementos.");
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return ele;
    }

    public  ArrayList<Elemento> consultarElementos() {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        ArrayList<Elemento> geral = new ArrayList<>();
        try {
            String consulta = "select * from elemento order by eleOrdem";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            
            while(resultado.next()) {
                Elemento ele = new Elemento();
                ele.setEleCodigo(resultado.getInt("eleCodigo"));
                ele.setEleNome(resultado.getString("eleNome"));
                ele.setEleOrdem(resultado.getInt("eleOrdem"));
                ele.setEleValorRecomendado(resultado.getDouble("eleValorRecomendado"));
                geral.add(ele);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ElementoControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar(conn);
        }
        return geral;
    }

    public String inserirElemento(Elemento ele) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "INSERT INTO elemento (eleNome, eleOrdem, eleValorRecomendado) VALUES (?, ?, ?)";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getEleNome());
            stm.setInt(2, ele.getEleOrdem());
            stm.setDouble(3, ele.getEleValorRecomendado());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = "erro";
        } finally {
            conexao.fechar(conn);
        }
        return resultado;
    }

    public void imprimirElementos(ArrayList<Elemento> ele){
        for (Elemento x : ele) { 
            x.toString();
        }
    }
    
    public String alterarElemento(Elemento ele){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "UPDATE elemento SET eleNome = ?, eleOrdem = ?, eleValorRecomendado = ? WHERE eleCodigo = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getEleNome());
            stm.setInt(2, ele.getEleOrdem());
            stm.setDouble(3, ele.getEleValorRecomendado());
            stm.setInt(4, ele.getEleCodigo());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = "erro";
        } 
        return resultado;
    }
    
    public String removerElemento(int elemento){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "DELETE FROM elemento WHERE eleCodigo = ?";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, elemento);
            
            stm.executeUpdate();
            resultado = "removido";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = "erro";
        } 
        return resultado;
    }

    public ElementoControle() {
    }
    
    
    
}
