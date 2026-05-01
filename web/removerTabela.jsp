<%@page import="modelo.Fabricante"%>
<%@page import="modelo.Produto"%>
<%@page import="modelo.TabelaNutricional"%>
<%@page import="controle.FabricanteControle"%>
<%@page import="controle.ProdutoControle"%>
<%@page import="controle.TabelaNutricionalControle"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    ProdutoControle pro = new ProdutoControle();
    FabricanteControle fab = new FabricanteControle();
    TabelaNutricionalControle tabc = new TabelaNutricionalControle();

    String acao = request.getParameter("acao");
    String resultado = "";

    if ("confirmar".equals(acao)) {
        String codigo = request.getParameter("tabCodigo");
        if (codigo != null) {
            int tabCodigo = Integer.parseInt(codigo);
            TabelaNutricional tn = tabc.consultarTabelaNutricionalCodigo(tabCodigo);
            int proCodigo = tn.getProCodigo();
            String resTab = tabc.removerTabelaNutricional(tabCodigo);
            String resPro = pro.removerProduto(proCodigo);
            if ("removido".equals(resTab) || "removido".equals(resPro)) {
                resultado = "Tabela nutricional removida com sucesso!";
            } else {
                resultado = "Erro ao remover tabela. Verifique se nao ha dependencias.";
            }
        }
    }

    ArrayList<TabelaNutricional> tabs = tabc.consultarTabelasNutricionais();
%>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Nutri - Remover Tabelas Nutricionais</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="main-layout">
    <div class="loader_bg"><div class="loader"><img src="images/loading.gif" alt="#"/></div></div>
    <%@include file="menu.jsp"%>

    <div class="three_box">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="box_text_CRUD">
                        <h3>Remover Tabelas Nutricionais</h3><br>

                        <% if (!resultado.isEmpty()) { %>
                            <div class="alert alert-info"><%= resultado %></div>
                        <% } %>

                        <% if (tabs.isEmpty()) { %>
                            <div class="alert alert-info">Nenhuma tabela nutricional cadastrada.</div>
                        <% } else { %>
                            <table class="table table-striped">
                                <thead>
                                    <tr><th>#</th><th>Produto</th><th>Fabricante</th><th>Acao</th></tr>
                                </thead>
                                <tbody>
                                    <% int counter = 0;
                                       for (TabelaNutricional c : tabs) {
                                           counter++;
                                           Produto prodInfo = pro.consultarProdutoCodigo(c.getProCodigo());
                                           String nomeProduto = prodInfo != null ? prodInfo.getProNome() : "Produto #" + c.getProCodigo();
                                           int fabCodigo = prodInfo != null ? prodInfo.getFabCodigo() : 1;
                                           String nomeFabricante = fab.getNomeFabricante(fabCodigo);
                                    %>
                                    <tr>
                                        <td><%= counter %></td>
                                        <td><%= nomeProduto %></td>
                                        <td><%= nomeFabricante %></td>
                                        <td>
                                            <a href="removerTabela.jsp?acao=confirmar&tabCodigo=<%= c.getTabCodigo() %>"
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Tem certeza que deseja remover a tabela do produto <%= nomeProduto.replaceAll("'", "\\\\'") %>?');">
                                                Remover
                                            </a>
                                        </td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        <% } %>

                        <br>
                        <a href="tabela.jsp" class="btn btn-secondary btn-sm">Voltar</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>
