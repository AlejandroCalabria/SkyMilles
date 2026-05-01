<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="conexao.ConexaoMySQL" %>
<%
    request.setCharacterEncoding("UTF-8");
    String confirm = request.getParameter("confirmar");
    ConexaoMySQL conexao = new ConexaoMySQL();
    Connection conn = conexao.conectar();
    StringBuilder log = new StringBuilder();

    if ("sim".equals(confirm)) {
        try {
            // 1. Deletar tabNutElemento de tabelas de produtos orfãos (sem fabricante válido)
            String sqlDelNutOrfãos = "DELETE tne FROM tabnutelemento tne " +
                    "INNER JOIN tabelanutricional tn ON tne.tabCodigo = tn.tabCodigo " +
                    "INNER JOIN produto p ON tn.proCodigo = p.proCodigo " +
                    "WHERE p.fabCodigo NOT IN (SELECT fabCodigo FROM fabricante)";
            PreparedStatement st = conn.prepareStatement(sqlDelNutOrfãos);
            int delNut = st.executeUpdate();
            log.append("Registros nutrientes de produtos órfãos: ").append(delNut).append("<br>");

            // 2. Deletar tabelanutricional de produtos orfãos
            String sqlDelTabOrfaos = "DELETE tn FROM tabelanutricional tn " +
                    "INNER JOIN produto p ON tn.proCodigo = p.proCodigo " +
                    "WHERE p.fabCodigo NOT IN (SELECT fabCodigo FROM fabricante)";
            st = conn.prepareStatement(sqlDelTabOrfaos);
            int delTab = st.executeUpdate();
            log.append("Tabelas nutricionais de produtos órfãos: ").append(delTab).append("<br>");

            // 3. Deletar produtos órfãos
            String sqlDelProdutos = "DELETE FROM produto WHERE fabCodigo NOT IN (SELECT fabCodigo FROM fabricante)";
            st = conn.prepareStatement(sqlDelProdutos);
            int delProdutos = st.executeUpdate();
            log.append("Produtos sem produtor deletados: ").append(delProdutos).append("<br>");

            // 4. Criar fabricante de teste
            String fabTeste = "Teste - Produtos de Exemplo";
            String checkFab = "SELECT fabCodigo FROM fabricante WHERE fabNome = ?";
            PreparedStatement chk = conn.prepareStatement(checkFab);
            chk.setString(1, fabTeste);
            ResultSet rsChk = chk.executeQuery();
            Integer fabCodigo = 0;
            if (rsChk.next()) {
                fabCodigo = rsChk.getInt("fabCodigo");
                log.append("<br>Fabricante de teste ja existe com codigo: ").append(fabCodigo).append("<br>");
            } else {
                String insFab = "INSERT INTO fabricante (fabNome, fabEndereco, cooCodigo, fabContato) VALUES (?, ?, 1, ?)";
                PreparedStatement stFab = conn.prepareStatement(insFab);
                stFab.setString(1, fabTeste);
                stFab.setString(2, "Endereco de teste");
                stFab.setString(3, "contato@teste.com");
                stFab.executeUpdate();

                rsChk = chk.executeQuery();
                if (rsChk.next()) {
                    fabCodigo = rsChk.getInt("fabCodigo");
                    log.append("<br>Fabricante de teste criado com codigo: ").append(fabCodigo).append("<br>");
                }
            }

            log.append("<br>Banco de dados limpo com sucesso!<br>");

        } catch (SQLException ex) {
            log.append("Erro SQL: ").append(ex.getMessage()).append("<br>");
            ex.printStackTrace();
        }
    }

    if (conn != null) { try { conn.close(); } catch (Exception e) {} }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Limpar Banco - Nutri IFTM</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
    <style>
        .clean-box { background: #fff; border: 1px solid #ddd; border-radius: 8px; padding: 20px; margin-top: 30px; max-width: 700px; margin: 30px auto; }
        .clean-success { color: green; background: #e8f5e9; padding: 15px; border-radius: 4px; border: 1px solid #a5d6a7; }
        .clean-warn { color: #b45309; background: #fff3e0; padding: 15px; border-radius: 4px; border: 1px solid #ffe0b2; }
    </style>
</head>
<body class="main-layout">
    <div class="loader_bg"><div class="loader"><img src="images/loading.gif" alt="#"/></div></div>
    <%@include file="menu.jsp"%>

    <div class="three_box">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="box_text_CRUD">
                        <div class="clean-box">
                            <h3>Limpeza do Banco de Dados</h3>
                            <p style="color:#666; font-size:13px;">Remove produtos sem produtor e cria um produtor de teste.</p>
                            <% if ("sim".equals(confirm)) { %>
                                <div class="clean-success">
                                    <b>Limpeza concluida!</b><br><br>
                                    <%= log.toString() %>
                                </div>
                                <br>
                                <a href="tabela.jsp" class="btn btn-primary">Ver Tabelas</a>
                                <a href="inserirReceita.jsp" class="btn btn-secondary">Nova Receita</a>
                            <% } else { %>
                                <div class="clean-warn">
                                    <b>Atencao!</b> Esta acao vai remover todos os produtos que nao tem um produtor (fabricante) vinculado.<br>
                                    Um produtor de teste chamado "Teste - Produtos de Exemplo" sera criado se nao existir.
                                </div>
                                <br>
                                <a href="?confirmar=sim" class="btn btn-danger">Sim, limpar banco de dados</a>
                                <a href="tabela.jsp" class="btn btn-secondary">Cancelar</a>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer>
        <div class="testimonial"><div class="containerP"><div class="row"><div class="col-md-12">
            <%@include file="rodape.jsp"%>
        </div></div></div></div>
    </footer>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="js/custom.js"></script>
</body>
</html>
