package controle;

import java.sql.*;
import java.util.ArrayList;
import conexao.ConexaoMySQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.UnidadeMedida;

public class UnidadeMedidaControle {
    public static void main(String[] args) {
        
      
    }
    
    public UnidadeMedida consultarUnidadeMedidaCodigo(int undCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        UnidadeMedida ele = new UnidadeMedida();
        try {
          String consulta = "select * from unidademedida where undCodigo = ?";

          stm = conn.prepareStatement(consulta);
          stm.setInt(1, undCodigo);
          resultado = stm.executeQuery();

          while(resultado.next()) {
            ele.setUndCodigo(resultado.getInt("undCodigo"));
            ele.setUndNome(resultado.getString("undNome"));
          }
        } catch (SQLException ex) {
          System.out.println("Não conseguiu consultar os dados das unidades de medidas.");
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return ele;
    }

    public  ArrayList<UnidadeMedida> consultarUnidadeMedida() {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        ArrayList<UnidadeMedida> geral = new ArrayList<>();
        try {
            String consulta = "select * from unidademedida";
            stm = conn.prepareStatement(consulta);
            resultado = stm.executeQuery();

            while(resultado.next()) {
                UnidadeMedida ele = new UnidadeMedida();
                ele.setUndCodigo(resultado.getInt("undCodigo"));
                ele.setUndNome(resultado.getString("undNome"));
                geral.add(ele);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnidadeMedidaControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return geral;
    }
    
    public String inserirUnidadeMedida(UnidadeMedida ele) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "INSERT INTO unidademedida (undNome) VALUES (?)";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getUndNome());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = "erro";
        } 
        return resultado;
    }
  
    
    public String alterarUnidadeMedida(UnidadeMedida ele){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        String resultado = "";
        try {
            String consulta = "UPDATE unidademedida SET undNome = ? WHERE undCodigo = ?";
            stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getUndNome());
            stm.setInt(2, ele.getUndCodigo());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = ex.getSQLState();
        } finally {
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return resultado;
    }
    
    public String removerUnidadeMedida(int elemento){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "DELETE FROM unidademedida WHERE undCodigo = ?";

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

    public UnidadeMedidaControle() {
    }
    
    
    
}
