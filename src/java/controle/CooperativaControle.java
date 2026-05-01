package controle;

import java.sql.*;
import java.util.ArrayList;
import conexao.ConexaoMySQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cooperativa;


public class CooperativaControle {
    public static void main(String[] args) {
        
    }
    
    public Cooperativa consultarCooperativaCodigo(int cooCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        Cooperativa ele = new Cooperativa();
        try {
          String consulta = "select * from cooperativa where cooCodigo = " + cooCodigo;

          Statement stm = conn.createStatement();
          ResultSet resultado = stm.executeQuery(consulta);

          while(resultado.next()) {
            ele.setCooCodigo(resultado.getInt("cooCodigo"));
            ele.setCooNome(resultado.getString("cooNome"));
            ele.setCooEndereco(resultado.getString("cooEndereco"));
            ele.setCooCidade(resultado.getString("cooCidade"));
            ele.setCooCNPJ(resultado.getString("cooCNPJ"));
          }
        } catch (SQLException ex) {
          System.out.println("Não conseguiu consultar os dados das cooperativas.");
        } finally {
            conexao.fechar(conn);
        } 
        return ele;
    }
    
    public  ArrayList<Cooperativa> consultarCooperativas() {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        ArrayList<Cooperativa> geral = new ArrayList<>();
        try {
            String consulta = "select * from cooperativa order by cooNome";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            
            while(resultado.next()) {
                Cooperativa ele = new Cooperativa();
                ele.setCooCodigo(resultado.getInt("cooCodigo"));
                ele.setCooNome(resultado.getString("cooNome"));
                ele.setCooEndereco(resultado.getString("cooEndereco"));
                ele.setCooCidade(resultado.getString("cooCidade"));
                ele.setCooCNPJ(resultado.getString("cooCNPJ"));
                geral.add(ele);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CooperativaControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar(conn);
        }
        return geral;
    }

    public String inserirCooperativa(Cooperativa ele) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "INSERT INTO cooperativa (cooNome, cooEndereco, cooCidade, cooCNPJ) VALUES (?,?,?,?)";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getCooNome());
            stm.setString(2, ele.getCooEndereco());
            stm.setString(3, ele.getCooCidade());
            stm.setString(4, ele.getCooCNPJ());
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

    public String alterarCooperativa(Cooperativa ele){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "UPDATE cooperativa SET cooNome = ?, cooEndereco = ?, cooCidade = ?, cooCNPJ = ? WHERE cooCodigo = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getCooNome());
            stm.setString(2, ele.getCooEndereco());
            stm.setString(3, ele.getCooCidade());
            stm.setString(4, ele.getCooCNPJ());
            stm.setInt(5, ele.getCooCodigo());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = "erro";
        } finally {
            conexao.fechar(conn);
        }
        return resultado;
    }

    public String removerCooperativa(int fab){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "DELETE FROM cooperativa WHERE cooCodigo = ?";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, fab);
            
            stm.executeUpdate();
            resultado = "removido";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = "erro";
        } finally {
            conexao.fechar(conn);
        }
        return resultado;
    }

    public CooperativaControle() {
    }
    
    
    
}
