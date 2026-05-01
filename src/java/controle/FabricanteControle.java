package controle;

import java.sql.*;
import java.util.ArrayList;
import conexao.ConexaoMySQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Fabricante;

public class FabricanteControle {
    public static void main(String[] args) {
        
    }
    
    public Fabricante consultarFabricanteCodigo(int fabCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        Fabricante ele = new Fabricante();
        try {
          String consulta = "select * from fabricante where fabCodigo = ?";

          stm = conn.prepareStatement(consulta);
          stm.setInt(1, fabCodigo);
          resultado = stm.executeQuery();

          while(resultado.next()) {
            ele.setFabCodigo(resultado.getInt("fabCodigo"));
            ele.setFabNome(resultado.getString("fabNome"));
            ele.setFabEndereco(resultado.getString("fabEndereco"));
            ele.setFabCooperativa(resultado.getInt("cooCodigo"));
            ele.setFabContato(resultado.getString("fabContato"));
          }
        } catch (SQLException ex) {
          System.out.println("Não conseguiu consultar os dados dos produtores.");
        } finally {
          conexao.fechar(resultado);
          conexao.fechar(stm);
          conexao.fechar(conn);
        }
        return ele;
    }

    public  ArrayList<Fabricante> consultarFabricantes() {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        ArrayList<Fabricante> geral = new ArrayList<>();
        try {
            String consulta = "select * from fabricante order by fabNome";
            stm = conn.prepareStatement(consulta);
            resultado = stm.executeQuery();

            while(resultado.next()) {
                Fabricante ele = new Fabricante();
                ele.setFabCodigo(resultado.getInt("fabCodigo"));
                ele.setFabNome(resultado.getString("fabNome"));
                ele.setFabEndereco(resultado.getString("fabEndereco"));
                ele.setFabCooperativa(resultado.getInt("cooCodigo"));
                ele.setFabContato(resultado.getString("fabContato"));
                geral.add(ele);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FabricanteControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return geral;
    }

    public String inserirFabricante(Fabricante ele) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "INSERT INTO fabricante (fabNome, fabEndereco, cooCodigo, fabContato) VALUES (?,?,?,?)";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getFabNome());
            stm.setString(2, ele.getFabEndereco());
            stm.setInt(3, ele.getFabCooperativa());
            stm.setString(4, ele.getFabContato());
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

    public void imprimirFabricante(Fabricante ele){
        System.out.println("Codigo: " + ele.getFabCodigo() + "\n" + 
                 "Nome: " +  ele.getFabNome() + "\n" +
                 "Endereco: " +  ele.getFabEndereco()+ "\n" +
                 "Cooperativa: " +  ele.getFabCooperativa()+ "\n");
    }
    
    public void imprimirFabricantes(ArrayList<Fabricante> ele){
        for (Fabricante x : ele) { 
            System.out.println("Codigo: " + x.getFabCodigo()+ "\nNome: " +  x.getFabNome()+ "\nEndereco: " +  x.getFabEndereco()+ "\nCooperativa: " +  x.getFabCooperativa());           
        }
    }
    
    public String alterarFabricante(Fabricante ele){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "UPDATE fabricante SET fabNome = ?, fabEndereco = ?, cooCodigo = ?, fabContato = ? WHERE fabCodigo = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getFabNome());
            stm.setString(2, ele.getFabEndereco());
            stm.setInt(3, ele.getFabCooperativa());
            stm.setString(4, ele.getFabContato());
            stm.setInt(5, ele.getFabCodigo());
            
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

    public String getNomeFabricante(int fab){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        Fabricante ele = new Fabricante();
        try {
          String consulta = "select * from fabricante where fabCodigo = ?";

          stm = conn.prepareStatement(consulta);
          stm.setInt(1, fab);
          resultado = stm.executeQuery();

          while(resultado.next()) {
            ele.setFabCodigo(resultado.getInt("fabCodigo"));
            ele.setFabNome(resultado.getString("fabNome"));
            ele.setFabEndereco(resultado.getString("fabEndereco"));
            ele.setFabCooperativa(resultado.getInt("cooCodigo"));
            ele.setFabContato(resultado.getString("fabContato"));
          }
        } catch (SQLException ex) {
          System.out.println("Não conseguiu consultar os dados dos produtores.");
        } finally {
          conexao.fechar(resultado);
          conexao.fechar(stm);
          conexao.fechar(conn);
        }
        return ele.getFabNome();
    }

    public String removerFabricante(int fab){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "DELETE FROM fabricante WHERE fabCodigo = ?";

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

    public FabricanteControle() {
    }
    
    
    
}
