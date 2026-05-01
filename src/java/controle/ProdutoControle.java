package controle;

import java.sql.*;
import java.util.ArrayList;
import conexao.ConexaoMySQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Produto;

public class ProdutoControle {
    public static void main(String[] args) {
        
    }
    
    public Produto consultarProdutoCodigo(int proCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        Produto ele = new Produto();
        try {
          String consulta = "select * from produto where proCodigo = ?";

          stm = conn.prepareStatement(consulta);
          stm.setInt(1, proCodigo);
          resultado = stm.executeQuery();

          while(resultado.next()) {
            ele.setProCodigo(resultado.getInt("proCodigo"));
            ele.setFabCodigo(resultado.getInt("fabCodigo"));
            ele.setProDataFabricacao(resultado.getString("proDataFabricacao"));
            ele.setProDataVencimento(resultado.getString("proDataVencimento"));
            ele.setProNome(resultado.getString("proNome"));
            ele.setProPeso(resultado.getDouble("proPeso"));
            ele.setProRecomendacoes(resultado.getString("proRecomendacoes"));
            ele.setProIngredientes(resultado.getString("proIngredientes"));
            ele.setProNomeFantasia(resultado.getString("proNomeFantasia"));
          }
        } catch (SQLException ex) {
          System.out.println("Não conseguiu consultar os dados dos produtos.");
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return ele;
    }

    public ArrayList<Produto> consultarProdutosFabricante(int fabCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;

        ArrayList<Produto> geral = new ArrayList<>();
        try {
            String consulta = "select * from produto where fabCodigo = ?";

            stm = conn.prepareStatement(consulta);
            stm.setInt(1, fabCodigo);
            resultado = stm.executeQuery();

            while(resultado.next()) {
                Produto ele = new Produto();
                ele.setProCodigo(resultado.getInt("proCodigo"));
                ele.setFabCodigo(resultado.getInt("fabCodigo"));
                ele.setProDataFabricacao(resultado.getString("proDataFabricacao"));
                ele.setProDataVencimento(resultado.getString("proDataVencimento"));
                ele.setProNome(resultado.getString("proNome"));
                ele.setProPeso(resultado.getDouble("proPeso"));
                ele.setProRecomendacoes(resultado.getString("proRecomendacoes"));
                ele.setProIngredientes(resultado.getString("proIngredientes"));
                ele.setProNomeFantasia(resultado.getString("proNomeFantasia"));
                geral.add(ele);
            }
        } catch (SQLException ex) {
          System.out.println("Não conseguiu consultar os dados dos produtos.");
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return geral;
    }

    public  ArrayList<Produto> consultarProdutos() {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        ArrayList<Produto> geral = new ArrayList<>();
        try {
            String consulta = "select * from produto order by proNome";
            stm = conn.prepareStatement(consulta);
            resultado = stm.executeQuery();

            while(resultado.next()) {
                Produto ele = new Produto();
                ele.setProCodigo(resultado.getInt("proCodigo"));
                ele.setFabCodigo(resultado.getInt("fabCodigo"));
                ele.setProDataFabricacao(resultado.getString("proDataFabricacao"));
                ele.setProDataVencimento(resultado.getString("proDataVencimento"));
                ele.setProNome(resultado.getString("proNome"));
                ele.setProPeso(resultado.getDouble("proPeso"));
                ele.setProRecomendacoes(resultado.getString("proRecomendacoes"));
                ele.setProIngredientes(resultado.getString("proIngredientes"));
                ele.setProNomeFantasia(resultado.getString("proNomeFantasia"));
                geral.add(ele);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return geral;
    }

    public String inserirProduto(Produto ele) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "INSERT INTO produto (fabCodigo, proNome, proPeso, proDataFabricacao, proDataVencimento, proRecomendacoes, proIngredientes, proNomeFantasia) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ele.getFabCodigo());
            stm.setString(2, ele.getProNome());
            stm.setDouble(3, ele.getProPeso());
            stm.setString(4, ele.getProDataFabricacao());
            stm.setString(5, ele.getProDataVencimento());
            stm.setString(6, ele.getProRecomendacoes());
            stm.setString(7, ele.getProIngredientes());
            stm.setString(8, ele.getProNomeFantasia());
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

    public String alterarProduto(Produto ele){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "UPDATE produto SET fabCodigo = ?, proNome = ?, proPeso = ?, proDataFabricacao = ?, proDataVencimento = ?, proRecomendacoes = ?, proIngredientes = ?, proNomeFantasia = ? WHERE proCodigo = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ele.getFabCodigo());
            stm.setString(2, ele.getProNome());
            stm.setDouble(3, ele.getProPeso());
            stm.setString(4, ele.getProDataFabricacao());
            stm.setString(5, ele.getProDataVencimento());
            stm.setString(6, ele.getProRecomendacoes());
            stm.setString(7, ele.getProIngredientes());
            stm.setString(8, ele.getProNomeFantasia());
            stm.setInt(9, ele.getProCodigo());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
          System.out.println(ex.getSQLState());
          resultado = ex.getSQLState();
        } finally {
            conexao.fechar(conn);
        }
        return resultado;
    }

    public String removerProduto(int pro){
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "DELETE FROM produto WHERE proCodigo = ?";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, pro);
            
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

    public String imprimirProduto(Produto ele){
        return "Codigo produto: " + ele.getProCodigo() + 
                    "\nNome: " + ele.getProNome() + 
                    "\nCodigo do produtor: " +  ele.getFabCodigo() +
                    "\nData da Fabricação: " +  ele.getProDataFabricacao()+
                    "\nData da Vencimento: " +  ele.getProDataVencimento()+
                    "\nRecomendacoes: " +  ele.getProRecomendacoes()+
                    "\nIngredientes: " +  ele.getProIngredientes()+
                    "\nNome Fantasia: " +  ele.getProNomeFantasia()+
                    "\nPeso: " +  ele.getProPeso();
    }
    
    public void imprimirProdutos(ArrayList<Produto> ele){
        for (Produto x : ele) { 
            System.out.println("Codigo produto: " + x.getProCodigo() + 
                    "\nNome: " + x.getProNome() + 
                    "\nCodigo do produtor: " +  x.getFabCodigo() +
                    "\nData da Fabricação: " +  x.getProDataFabricacao()+
                    "\nData da Vencimento: " +  x.getProDataVencimento()+
                    "\nPeso: " +  x.getProPeso());
        }
    }

    public ProdutoControle() {
    }
    
    
    
}
