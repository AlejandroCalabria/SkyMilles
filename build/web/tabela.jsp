<%@page import="controle.UnidadeMedidaControle"%>
<%@page import="modelo.UnidadeMedida"%>
<%@page import="controle.ProdutoControle"%>
<%@page import="controle.TabelaNutricionalControle"%>
<%@page import="controle.ElementoControle"%>
<%@page import="controle.TabNutElementoControle"%>
<%@page import="modelo.Fabricante"%>
<%@page import="modelo.Produto"%>
<%@page import="modelo.TabNutElemento"%>
<%@page import="modelo.TabelaNutricional"%>
<%@page import="modelo.Elemento"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="controle.FabricanteControle" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Date" %>
<%
    ProdutoControle pro = new ProdutoControle();
    FabricanteControle fab = new FabricanteControle();
    ElementoControle nc = new ElementoControle();
    TabelaNutricionalControle tabc = new TabelaNutricionalControle();
    TabNutElementoControle tnec = new TabNutElementoControle();
    ArrayList<Fabricante> fabs = fab.consultarFabricantes();
    ArrayList<TabelaNutricional> tabs = new ArrayList<>();
    String fabricante = request.getParameter("fabricante");
    String fabSelecionado = null;
    if (fabricante != null && !fabricante.isEmpty()) {
        tabs = tabc.consultarTabelasNutricionaisFabricante(Integer.parseInt(fabricante));
        fabSelecionado = fabricante;
    } else {
        tabs = tabc.consultarTabelasNutricionais();
    }
%>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Nutri - IFTM - Tabelas Nutricionais</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/responsive.css">
        <link rel="stylesheet" href="css/jquery.mCustomScrollbar.min.css">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
        <style>
            .rotulo-card {
                border: 2px solid #333;
                padding: 12px;
                margin-bottom: 20px;
                background: #fff;
                font-family: Arial, Helvetica, sans-serif;
                max-width: 550px;
                border-radius: 4px;
            }
            .rotulo-titulo { font-size: 12pt; font-weight: bold; text-align: center; border-bottom: 3px solid #333; padding: 4px 0; margin-bottom: 4px; }
            .rotulo-info { font-size: 9pt; border-bottom: 3px solid #333; padding-bottom: 4px; margin-bottom: 4px; }
            .tabela-anvisa { border-collapse: collapse; width: 100%; border-top: 2px solid #333; }
            .tabela-anvisa th, .tabela-anvisa td { border-top: 2px solid #333; padding: 2px 4px; font-size: 9pt; }
            .tabela-anvisa td:last-child, .tabela-anvisa th:last-child { text-align: right; }
            .tabela-anvisa th:last-child { width: 50px; }
            .rotulo-nota { font-size: 7pt; margin-top: 6px; text-align: center; border-top: 3px solid #333; padding-top: 4px; }
            .fab-filters { background: #f5f5f5; padding: 12px; border-radius: 6px; margin-bottom: 20px; }
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
                            <h3>Gerenciamento de Tabelas Nutricionais</h3>
                            <br>

                            <!-- Filtro por Fabricante -->
                            <div class="fab-filters">
                                <form action="tabela.jsp" method="GET" class="form-inline">
                                    <label><b>Filtrar por produtor:</b></label>&nbsp;
                                    <select name='fabricante' class="form-control" style="width:auto; display:inline-block;">
                                        <option value="">Todos os produtos</option>
                                        <% for (Fabricante c : fabs) {
                                            String sel = (fabSelecionado != null && fabSelecionado.equals(String.valueOf(c.getFabCodigo()))) ? "selected" : "";
                                        %>
                                            <option value='<%= c.getFabCodigo() %>' <%=sel%>><%= c.getFabNome() %></option>
                                        <% } %>
                                    </select>&nbsp;
                                    <input type="submit" value="Filtrar" class="btn btn-primary btn-sm">
                                </form>
                            </div>

                            <% if (tabs.isEmpty()) { %>
                                <div class="alert alert-info">Nenhuma tabela nutricional encontrada.
                                    <a href="inserirReceita.jsp">Criar uma nova</a> ou
                                    <a href="inserirTabela.jsp">inserir manualmente</a>.
                                </div>
                            <% } else { %>
                                <p><b><%= tabs.size() %> tabela(s) nutricional(is) encontrada(s)</b></p>
                                <% int counter = 0;
                                   for (TabelaNutricional c : tabs) {
                                       counter++;

                // Buscar info do produto
                Produto prodInfo = pro.consultarProdutoCodigo(c.getProCodigo());
                String nomeProduto = prodInfo != null ? prodInfo.getProNome() : "Produto #" + c.getProCodigo();
                int fabCodigo = prodInfo != null ? prodInfo.getFabCodigo() : 1;
                String nomeFabricante = fab.getNomeFabricante(fabCodigo);

                UnidadeMedidaControle unid = new UnidadeMedidaControle();
                String undNome = unid.consultarUnidadeMedidaCodigo(c.getUndCodigo()).getUndNome();
                                %>
                                <div style="margin-bottom: 25px;">
                                    <div style="display:flex; align-items:center; justify-content:space-between;">
                                        <div>
                                            <b><%= counter %>. <%= nomeProduto %></b>
                                            <span style="color:#666; margin-left:10px; font-size:12px;">Produtor: <%= nomeFabricante %></span>
                                        </div>
                                        <div>
                                            <a href="gerarPDF.jsp?tabCodigo=<%= c.getTabCodigo() %>" target="_blank" class="btn btn-danger btn-sm" title="Gerar PDF">
                                                <i class="fa fa-file-pdf-o"></i> PDF
                                            </a>
                                        </div>
                                    </div>
                                    <details style="margin-top: 5px;">
                                        <summary style="cursor:pointer; color:#007bff; font-size:13px;">Clique para visualizar o rótulo nutricional</summary>
                                        <div class="rotulo-card" style="margin-top: 10px;">
                                            <div class="rotulo-titulo">INFORMACAO NUTRICIONAL</div>
                                            <div class="rotulo-info">
                                                Produto: <%= nomeProduto %><br>
                                                Porcao: <%= c.getTabPorcao() %> <%= undNome %>
                                                (<%= (c.getTabTotalPorcao() > 0) ? (int) c.getTabTotalPorcao() : "?" %> porcoes por embalagem)
                                            </div>
                                            <table class="tabela-anvisa">
                                                <tr>
                                                    <th></th>
                                                    <th>Por 100<%= undNome %></th>
                                                    <th>Por porcao</th>
                                                    <th>%VD</th>
                                                </tr>
                                                <tr>
                                                    <td><b>Valor energetico (kcal)</b></td>
                                                    <td><%= String.format(Locale.US, "%.1f", c.getTabValorEnergetico()) %></td>
                                                    <td><%= String.format(Locale.US, "%.1f", c.getTabValorEnergeticoPorcao()) %></td>
                                                    <td><%= String.format(Locale.US, "%.0f", c.getTabVD()) %></td>
                                                </tr>
                                                <% for (TabNutElemento d : c.getTneElementos()) {
                                                    if (d.getTneValor() == 0 && d.getTneValorPadrao() == 0) continue;
                                                    String vdStr = "";
                                                    if (d.getEleCodigo() != 2) { // Acúcares - sem VD
                                                        if (d.getTneVD() > 0) vdStr = String.format("%.0f", d.getTneVD());
                                                        else vdStr = "-";
                                                    } else {
                                                        vdStr = "-";
                                                    }
                                                %>
                                                <tr>
                                                    <td><b><%= nc.consultarElementoCodigo(d.getEleCodigo()).getEleNome() %></b></td>
                                                    <td><%= String.format("%.1f", d.getTneValorPadrao()) %></td>
                                                    <td><%= String.format("%.1f", d.getTneValor()) %></td>
                                                    <td><%= vdStr %></td>
                                                </tr>
                                                <% } %>
                                            </table>
                                            <p class="rotulo-nota">* Percentuais de Valores Diarios com base em dieta de 2.000 kcal.</p>
                                        </div>
                                    </details>
                                </div>
                                <% } %>
                            <% } %>

                            <hr>
                            <a href='inserirTabela.jsp' class='btn btn-secondary btn-sm'>
                            <svg class='icon' viewBox='0 0 24 24'>
                            <path d='M12 5v14'></path><path d='M5 12h14'></path>
                            </svg>Inserir uma nova tabela nutricional</a>
                            <a href='alterarTabela.jsp' class='btn btn-secondary btn-sm'>
                            <svg class='icon' viewBox='0 0 24 24'>
                            <path d='M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7'></path>
                            <path d='M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z'></path>
                            </svg>Alterar dados das tabelas nutricionais</a>
                            <a href='removerTabela.jsp' class='btn btn-secondary-remove btn-sm'>
                            <svg class='icon' viewBox='0 0 24 24'>
                            <polyline points='3,6 5,6 21,6'></polyline>
                            <path d='M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2'></path>
                            </svg>Remover tabelas nutricionais cadastradas</a>
                            <a href='imprimirTabela.jsp' class='btn btn-primary btn-sm'>
                            <svg class='icon' viewBox='0 0 24 24'>
                            <path d='M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7'></path>
                            <path d='M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z'></path>
                            </svg>Imprimir tabelas nutricionais</a>
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
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/jquery-3.0.0.min.js"></script>
        <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>
