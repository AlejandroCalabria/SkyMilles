package conexao;

import java.sql.*;

public class ConexaoMySQL {
    public ConexaoMySQL () {} // construtor de classe

    public Connection conectar() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ip = "127.0.0.1";
            String us = "root";
            String bd = "nutricional";
            String pw = "";
            con = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + bd, us, pw);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar ao banco: " + ex.getMessage());
            ex.printStackTrace();
        }
        return con;
    }

    public void fechar(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar conexao: " + ex.getMessage());
        }
    }

    public void fechar(PreparedStatement ps) {
        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar PreparedStatement: " + ex.getMessage());
        }
    }

    public void fechar(Statement stm) {
        try {
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar Statement: " + ex.getMessage());
        }
    }

    public void fechar(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar ResultSet: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        ConexaoMySQL teste = new ConexaoMySQL();
        Connection c = teste.conectar();
        System.out.println("Conexao: " + c);
        teste.fechar(c);
    }

}
