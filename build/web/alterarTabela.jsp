<%@page import="modelo.Fabricante"%>
<%@page import="modelo.Produto"%>
<%@page import="modelo.TabelaNutricional"%>
<%@page import="modelo.UnidadeMedida"%>
<%@page import="modelo.TabNutElemento"%>
<%@page import="modelo.Elemento"%>
<%@page import="controle.FabricanteControle"%>
<%@page import="controle.ProdutoControle"%>
<%@page import="controle.TabelaNutricionalControle"%>
<%@page import="controle.UnidadeMedidaControle"%>
<%@page import="controle.ElementoControle"%>
<%@page import="controle.TabNutElementoControle"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");

    TabelaNutricionalControle tabc = new TabelaNutricionalControle();
    ProdutoControle pro = new ProdutoControle();
    FabricanteControle fab = new FabricanteControle();
    UnidadeMedidaControle umc = new UnidadeMedidaControle();

    String acao = request.getParameter("acao");
    String resultado = "";

    TabelaNutricional tnParaEditar = null;
    Produto prodEdit = null;

    if ("carregar".equals(acao)) {
        String param = request.getParameter("tabCodigo");
        if (param != null) {
            int tabCodigo = Integer.parseInt(param);
            tnParaEditar = tabc.consultarTabelaNutricionalCodigo(tabCodigo);
            prodEdit = pro.consultarProdutoCodigo(tnParaEditar.getProCodigo());
        }
    } else if ("salvarAlteracao".equals(acao)) {
        String tabCodigo = request.getParameter("tabCodigo");
        String proNome = request.getParameter("proNome");
        String proPeso = request.getParameter("proPeso");
        String tabPorcao = request.getParameter("tabPorcao");
        String fabCodigo = request.getParameter("fabCodigo");
        String undCodigo = request.getParameter("undCodigo");

        if (tabCodigo != null) {
            int codigo = Integer.parseInt(tabCodigo);
            TabelaNutricional tn = tabc.consultarTabelaNutricionalCodigo(codigo);
            int proCod = tn.getProCodigo();

            Produto pAtualizado = new Produto();
            pAtualizado.setProCodigo(proCod);
            pAtualizado.setProNome(proNome);
            pAtualizado.setProPeso(proPeso != null ? Double.parseDouble(proPeso.replaceAll(",", ".")) : 0);
            pAtualizado.setFabCodigo(fabCodigo != null ? Integer.parseInt(fabCodigo) : 1);

            String res = pro.alterarProduto(pAtualizado);
            if ("alterado".equals(res)) {
                resultado = "Produto atualizado com sucesso!";
                tnParaEditar = tabc.consultarTabelaNutricionalCodigo(codigo);
                prodEdit = pro.consultarProdutoCodigo(proCod);
            } else {
                resultado = "Erro ao atualizar produto.";
            }
        }
    }

    ArrayList<TabelaNutricional> tabs = tabc.consultarTabelasNutricionais();
    ArrayList<Fabricante> fabs = fab.consultarFabricantes();
    ArrayList<UnidadeMedida> unds = umc.consultarUnidadeMedida();
%>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Nutri - Alterar Tabelas Nutricionais</title>
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
                        <h3>Alterar Dados das Tabelas Nutricionais</h3><br>

                        <% if (!resultado.isEmpty()) { %>
                            <div class="alert alert-info"><%= resultado %></div>
                        <% } %>

                        <% if (prodEdit != null && tnParaEditar != null) { %>
                            <div class="card p-4" style="max-width:600px;">
                                <h4>Editando: <%= prodEdit.getProNome() %></h4>
                                <form action="alterarTabela.jsp" method="POST">
                                    <input type="hidden" name="acao" value="salvarAlteracao">
                                    <input type="hidden" name="tabCodigo" value="<%= tnParaEditar.getTabCodigo() %>">
                                    <div class="form-group">
                                        <label>Nome do Produto:</label>
                                        <input type="text" name="proNome" class="form-control" value="<%= prodEdit.getProNome() %>" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Peso Total (g):</label>
                                        <input type="text" name="proPeso" class="form-control" value="<%= String.format(Locale.US, "%.1f", prodEdit.getProPeso()) %>" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Fabricante:</label>
                                        <select name="fabCodigo" class="form-control">
                                            <% for (Fabricante f : fabs) { %>
                                                <option value="<%= f.getFabCodigo() %>" <%= (f.getFabCodigo() == prodEdit.getFabCodigo()) ? "selected" : "" %>><%= f.getFabNome() %></option>
                                            <% } %>
                                        </select>
                                    </div>
                                    <br>
                                    <button type="submit" class="btn btn-primary">Salvar Alteracoes</button>
                                    <a href="alterarTabela.jsp" class="btn btn-secondary">Cancelar</a>
                                </form>
                            </div>
                        <% } else { %>
                            <table class="table table-striped">
                                <thead>
                                    <tr><th>#</th><th>Produto</th><th>Fabricante</th><th>Porcao</th><th>Acao</th></tr>
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
                                        <td><%= String.format(Locale.US, "%.0f", c.getTabPorcao()) %></td>
                                        <td>
                                            <a href="alterarTabela.jsp?acao=carregar&tabCodigo=<%= c.getTabCodigo() %>"
                                               class="btn btn-warning btn-sm">
                                                Editar
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
