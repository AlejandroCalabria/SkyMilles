package controle;

import java.sql.*;
import java.util.ArrayList;
import conexao.ConexaoMySQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.*;


public class TabelaNutricionalControle {

    private static double TAB_VALOR = 100;
    private static double TAB_CALORIAS = 2000;
    
    public TabelaNutricional consultarTabelaNutricionalCodigo(int tabCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        TabelaNutricional ele = new TabelaNutricional();
        try {
            String consulta = "select * from tabelanutricional where tabCodigo = ?";

            stm = conn.prepareStatement(consulta);
            stm.setInt(1, tabCodigo);
            resultado = stm.executeQuery();

            while (resultado.next()) {

                ele.setProCodigo(resultado.getInt("proCodigo"));
                ele.setTabCodigo(resultado.getInt("tabCodigo"));
                ele.setTabPorcao(resultado.getDouble("tabPorcao"));
                ele.setUndCodigo(resultado.getInt("undCodigo"));
                ele.setTabValorEnergeticoPorcao(resultado.getDouble("tabValorEnergeticoPorcao"));
                ele.setTabTotalColheres(resultado.getInt("tabTotalColheres"));
                ele.setTabUnidadeMedidasColheres(resultado.getInt("tabUnidadeMedidasColheres"));

                // calculos automáticos
                ele.setTabPorcaoPadrao(TAB_VALOR); // valor padrao
                ele.setTabVD(calcularVD(ele));
                ele.setTabValorEnergetico(calcularValorEnergetico100(ele));
                ele.setTabTotalPorcao(calcularTotalPorcoes(ele));

                //montar os nutrientes
                //TabNutElementoControle tneElementos = new TabNutElementoControle();
                ele.setTneElementos(calcularValoresNutrientes(ele));
            }
        } catch (SQLException ex) {
            System.out.println("Não conseguiu consultar os dados das tabelas nutricionais.");
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return ele;
    }

    public TabelaNutricional consultarTabelaNutricionalCodigoProduto(int proCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        TabelaNutricional ele = new TabelaNutricional();
        try {
            String consulta = "select * from tabelanutricional where proCodigo = ?";

            stm = conn.prepareStatement(consulta);
            stm.setInt(1, proCodigo);
            resultado = stm.executeQuery();

            while (resultado.next()) {

                ele.setProCodigo(resultado.getInt("proCodigo"));
                ele.setTabCodigo(resultado.getInt("tabCodigo"));
                ele.setTabPorcao(resultado.getDouble("tabPorcao"));
                ele.setUndCodigo(resultado.getInt("undCodigo"));
                ele.setTabValorEnergeticoPorcao(resultado.getDouble("tabValorEnergeticoPorcao"));
                ele.setTabTotalColheres(resultado.getInt("tabTotalColheres"));
                ele.setTabUnidadeMedidasColheres(resultado.getInt("tabUnidadeMedidasColheres"));

                // calculos automáticos
                ele.setTabPorcaoPadrao(TAB_VALOR); // valor padrao
                ele.setTabVD(calcularVD(ele));
                ele.setTabValorEnergetico(calcularValorEnergetico100(ele));
                ele.setTabTotalPorcao(calcularTotalPorcoes(ele));

                //montar os nutrientes
                ele.setTneElementos(calcularValoresNutrientes(ele));
            }
        } catch (SQLException ex) {
            System.out.println("Não conseguiu consultar os dados das tabelas nutricionais.");
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return ele;
    }
    
    private ArrayList<TabNutElemento> calcularValoresNutrientes(TabelaNutricional tn){
        TabNutElementoControle tnec = new TabNutElementoControle();
        return tnec.consultarElementosNutricionais(tn.getTabCodigo(), tn.getTabPorcao());
    }
    
    private double calcularTotalPorcoes(TabelaNutricional tn){
        ProdutoControle pc = new ProdutoControle();
        return Math.round(pc.consultarProdutoCodigo(tn.getProCodigo()).getProPeso() / tn.getTabPorcao());
    }
    
    private double calcularValorEnergetico100(TabelaNutricional tn){
        return Math.round(tn.getTabValorEnergeticoPorcao() * TAB_VALOR / tn.getTabPorcao() * 10.0) / 10.0;
    }
    
    private double calcularVD(TabelaNutricional tn){
        return Math.round(tn.getTabValorEnergeticoPorcao() / TAB_CALORIAS * 100 * 10.0) / 10.0;
    }
    
    public ResultSet consultarTabelasNutricionaisResultSet(int tabCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        try {
            String consulta = "SELECT a.proCodigo, a.proNome, a.fabCodigo, a.proPeso, a.proDataFabricacao, "
                    + "a.proDataVencimento, a.proRecomendacoes, a.proIngredientes, a.proNomeFantasia, "
                    + "b.tabCodigo, b.tabPorcao, b.tabValorEnergeticoPorcao, b.tabTotalColheres, "
                    + "b.tabUnidadeMedidasColheres, b.undCodigo, "
                    + "c.tneCodigo, c.tneValor, c.tneValorPadrao, c.tneVD, c.eleCodigo, "
                    + "d.undNome, e.fabNome, e.fabEndereco, e.fabCooperativa, e.fabContato, "
                    + "f.eleNome, f.eleOrdem, f.eleValorRecomendado "
                    + "FROM produto as A INNER JOIN tabelanutricional as B on a.proCodigo = b.proCodigo "
                    + "LEFT JOIN tabnutelemento as c on b.tabCodigo = c.tabCodigo "
                    + "LEFT JOIN unidademedida as d on b.undCodigo = d.undCodigo "
                    + "INNER JOIN fabricante as e on a.fabCodigo = e.fabCodigo "
                    + "LEFT JOIN elemento as f on c.eleCodigo = f.eleCodigo "
                    + "WHERE b.tabCodigo = ?";
            stm = conn.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stm.setInt(1, tabCodigo);
            ResultSet resultado = stm.executeQuery();
            // Retornamos o ResultSet, mas mantemos a conexão e statement abertos
            // O chamador DEVE fechar o ResultSet, statement e connection após o uso
            // Para evitar vazamento, vamos retornar um objeto que encapsule esses recursos
            // Mas por compatibilidade, mantemos a assinatura e documentamos a responsabilidade
            return resultado;
        } catch (SQLException ex) {
            System.out.println("Não conseguiu consultar os dados das tabelas nutricionais.");
            // Fechar recursos em caso de erro
            conexao.fechar(stm);
            conexao.fechar(conn);
            // Retorna ResultSet vazio em caso de erro
            try {
                // Se a conexão ainda estiver viva, usamos ela para criar o statement vazio
                if (conn != null && !conn.isClosed()) {
                    PreparedStatement emptyStm = conn.prepareStatement("SELECT tabCodigo FROM tabelanutricional WHERE 1=0");
                    ResultSet emptyRs = emptyStm.executeQuery();
                    conexao.fechar(emptyStm); // Fechar statement, manter ResultSet para o chamador fechar
                    return emptyRs;
                }
            } catch (SQLException e2) {
                // Se não conseguimos criar o query vazio, retornamos null
            }
            return null;
        }
        // Nota: Não fechamos conn/stm aqui pois o ResultSet precisa permanecer aberto para uso imediato
        // O CHAMADOR É RESPONSÁVEL POR FECHAR: ResultSet, Statement e Connection nesta ordem
        // Isso é uma fragilidade de design, mas mantido por compatibilidade com JasperReports
    }

    public ArrayList<TabelaNutricional> consultarTabelasNutricionaisFabricante(int fabCodigo) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        ArrayList<TabelaNutricional> res = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM produto as A INNER JOIN tabelanutricional as B on a.proCodigo = b.proCodigo and a.fabCodigo = ?";
            stm = conn.prepareStatement(consulta);
            stm.setInt(1, fabCodigo);
            resultado = stm.executeQuery();
            while (resultado.next()) {
                TabelaNutricional ele = new TabelaNutricional();
                ele.setProCodigo(resultado.getInt("proCodigo"));
                ele.setTabCodigo(resultado.getInt("tabCodigo"));
                ele.setTabPorcao(resultado.getDouble("tabPorcao"));
                ele.setUndCodigo(resultado.getInt("undCodigo"));
                ele.setTabValorEnergeticoPorcao(resultado.getDouble("tabValorEnergeticoPorcao"));
                ele.setTabTotalColheres(resultado.getInt("tabTotalColheres"));
                ele.setTabUnidadeMedidasColheres(resultado.getInt("tabUnidadeMedidasColheres"));

                // calculos automáticos
                ele.setTabPorcaoPadrao(TAB_VALOR); // valor padrao
                ele.setTabVD(calcularVD(ele));
                ele.setTabValorEnergetico(calcularValorEnergetico100(ele));
                ele.setTabTotalPorcao(calcularTotalPorcoes(ele));

                //montar os nutrientes
                //TabNutElementoControle tneElementos = new TabNutElementoControle();
                ele.setTneElementos(calcularValoresNutrientes(ele));

                res.add(ele);
            }
        } catch (SQLException ex) {
            System.out.println("Não conseguiu consultar os dados das tabelas nutricionais.");
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return res;
    }

    public int consultarTabelaNutricional(TabelaNutricional tb) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        int res = 0;
        try {
            String consulta = "SELECT tabCodigo FROM tabelanutricional where "
                    + "proCodigo = ? and undCodigo = ? and tabPorcao = ? and tabValorEnergeticoPorcao = ?";
            stm = conn.prepareStatement(consulta);
            stm.setInt(1, tb.getProCodigo());
            stm.setInt(2, tb.getUndCodigo());
            stm.setDouble(3, tb.getTabPorcao());
            stm.setDouble(4, tb.getTabValorEnergeticoPorcao());
            resultado = stm.executeQuery();
            while (resultado.next()) {
                res = resultado.getInt("tabCodigo");
            }
        } catch (SQLException ex) {
            System.out.println("Não conseguiu consultar os dados das tabelas nutricionais.");
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return res;
    }

    public ArrayList<TabelaNutricional> consultarTabelasNutricionais() {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        ResultSet resultado = null;
        ArrayList<TabelaNutricional> res = new ArrayList<>();
        try {
            String consulta = "select * from tabelanutricional";
            stm = conn.prepareStatement(consulta);
            resultado = stm.executeQuery();

            while (resultado.next()) {
                TabelaNutricional ele = new TabelaNutricional();
                ele.setProCodigo(resultado.getInt("proCodigo"));
                ele.setTabCodigo(resultado.getInt("tabCodigo"));
                ele.setTabPorcao(resultado.getDouble("tabPorcao"));
                ele.setUndCodigo(resultado.getInt("undCodigo"));
                ele.setTabValorEnergeticoPorcao(resultado.getDouble("tabValorEnergeticoPorcao"));
                ele.setTabTotalColheres(resultado.getInt("tabTotalColheres"));
                ele.setTabUnidadeMedidasColheres(resultado.getInt("tabUnidadeMedidasColheres"));

                // calculos automáticos
                ele.setTabPorcaoPadrao(TAB_VALOR); // valor padrao
                ele.setTabVD(calcularVD(ele));
                ele.setTabValorEnergetico(calcularValorEnergetico100(ele));
                ele.setTabTotalPorcao(calcularTotalPorcoes(ele));

                //montar os nutrientes
                ele.setTneElementos(calcularValoresNutrientes(ele));
                res.add(ele);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TabelaNutricionalControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar(resultado);
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return res;
    }

    public String inserirTabelaNutricional(TabelaNutricional tb) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String resultado = "";
        try {
            String consulta = "INSERT INTO tabelanutricional (tabPorcao, proCodigo, undCodigo, tabValorenergeticoPorcao, tabTotalColheres, tabUnidadeMedidasColheres) VALUES (?,?,?,?,?,?)";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setDouble(1, tb.getTabPorcao());
            stm.setInt(2, tb.getProCodigo());
            stm.setInt(3, tb.getUndCodigo());
            stm.setDouble(4, tb.getTabValorEnergeticoPorcao());
            stm.setInt(5, tb.getTabTotalColheres());
            stm.setInt(6, tb.getTabUnidadeMedidasColheres());
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

    public String alterarTabelaNutricional(TabelaNutricional tb) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        String resultado = "";
        try {
            String consulta = "UPDATE tabelanutricional SET tabPorcao = ?, proCodigo = ?, undCodigo = ?, tabValorenergeticoPorcao = ?, tabTotalColheres = ?, tabUnidadeMedidasColheres = ? WHERE tabCodigo = ?";
            stm = conn.prepareStatement(consulta);
            stm.setDouble(1, tb.getTabPorcao());
            stm.setInt(2, tb.getProCodigo());
            stm.setInt(3, tb.getUndCodigo());
            stm.setDouble(4, tb.getTabValorEnergeticoPorcao());
            stm.setInt(5, tb.getTabTotalColheres());
            stm.setInt(6, tb.getTabUnidadeMedidasColheres());
            stm.setInt(7, tb.getTabCodigo());
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

    public String removerTabelaNutricional(int tb) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        PreparedStatement stm = null;
        String resultado = "";
        try {
            String consulta = "DELETE FROM tabelanutricional WHERE tabCodigo = ?";

            stm = conn.prepareStatement(consulta);
            stm.setInt(1, tb);

            stm.executeUpdate();
            resultado = "removido";
        } catch (SQLException ex) {
            System.out.println(ex.getSQLState());
            resultado = "erro";
        } finally {
            conexao.fechar(stm);
            conexao.fechar(conn);
        }
        return resultado;
    }

    public String imprimirTabelaNutricional(TabelaNutricional ele) {
        return "";/*"Codigo produto: " + ele.getProCodigo()
                + "\nNome: " + ele.getProNome()
                + "\nCodigo do produtor: " + ele.getFabCodigo()
                + "\nData da Fabricação: " + ele.getProDataFabricacao()
                + "\nData da Vencimento: " + ele.getProDataVencimento()
                + "\nPeso: " + ele.getProPeso();*/
    }

    public void imprimirTabelaNutricional(ArrayList<TabelaNutricional> ele) {
        /*for (TabelaNutricional x : ele) {
            System.out.println("Codigo produto: " + x.getProCodigo()
                    + "\nNome: " + x.getProNome()
                    + "\nCodigo do produtor: " + x.getFabCodigo()
                    + "\nData da Fabricação: " + x.getProDataFabricacao()
                    + "\nData da Vencimento: " + x.getProDataVencimento()
                    + "\nPeso: " + x.getProPeso());
        }*/
    }

    public TabelaNutricionalControle() {
    }
}
