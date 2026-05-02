<%@page import="controle.TabNutElementoControle"%>
<%@page import="modelo.TabNutElemento"%>
<%@page import="controle.TabelaNutricionalControle"%>
<%@page import="modelo.TabelaNutricional"%>
<%@page import="modelo.Elemento"%>
<%@page import="controle.ElementoControle"%>
<%@page import="modelo.UnidadeMedida"%>
<%@page import="controle.UnidadeMedidaControle"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@page import="modelo.Produto" %>
<%@page import="modelo.Fabricante" %>
<%@page import="controle.ProdutoControle" %>
<%@page import="controle.FabricanteControle" %>
<%@page import="java.util.ArrayList" %>
<%
    ProdutoControle pro = new ProdutoControle();
    ElementoControle ec = new ElementoControle();
    FabricanteControle fab = new FabricanteControle();
    ArrayList<Produto> produtos = pro.consultarProdutos();
    UnidadeMedidaControle und = new UnidadeMedidaControle();
    TabelaNutricionalControle ttc = new TabelaNutricionalControle();
    String fabricante = request.getParameter("prod");
    if (fabricante != null) {
        TabelaNutricional ele = new TabelaNutricional();
        ele.setProCodigo(Integer.parseInt(request.getParameter("prod")));
        ele.setTabPorcao(Double.parseDouble(request.getParameter("por")));
        ele.setTabValorEnergeticoPorcao(Double.parseDouble(request.getParameter("ve")));
        ele.setUndCodigo(Integer.parseInt(request.getParameter("uni")));
        ele.setTabUnidadeMedidasColheres(Integer.parseInt(request.getParameter("uni_col")));
        ele.setTabTotalColheres(Integer.parseInt(request.getParameter("col")));
        
        ttc.inserirTabelaNutricional(ele);
        int tabCodigo =  ttc.consultarTabelaNutricional(ele);
        int i = 0;
        for (Elemento c : ec.consultarElementos()){
            TabNutElementoControle nutrientesc = new TabNutElementoControle();
            TabNutElemento nutrientes = new TabNutElemento();
            if(request.getParameter("vp"+i) == ""){
                
            }
            else{
                nutrientes.setEleCodigo(Integer.parseInt(request.getParameter("n"+i)));
                
                nutrientes.setTabCodigo(tabCodigo);
                nutrientes.setTneValor(Double.parseDouble(request.getParameter("vp"+i)));
                nutrientesc.inserirElementosNutricionais(nutrientes);
            }
            i++;
        }
        out.println("<script type='text/javascript'>");
        out.println("alert('Produto inserido com sucesso!')");
        out.println("</script>");
    }
%>
<html lang="en">
    <head>
        <!-- basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- mobile metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <!-- site metas -->
        <title>Nutri - IFTM</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content="">
        <!-- bootstrap css -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <!-- style css -->
        <link rel="stylesheet" href="css/style.css">
        <!-- Responsive-->
        <link rel="stylesheet" href="css/responsive.css">
        <!-- fevicon -->
        <!-- Scrollbar Custom CSS -->
        <link rel="stylesheet" href="css/jquery.mCustomScrollbar.min.css">
        <!-- Tweaks for older IEs-->
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->  
    </head>
    <!-- body -->
    <body class="main-layout">
        <!-- loader  -->
        <div class="loader_bg">
            <div class="loader"><img src="images/loading.gif" alt="#" /></div>
        </div>

        <%@include file="menu.jsp"%>
        <!-- end banner -->
        <!-- three_box -->
        <form action="inserirTabela.jsp" method="POST">
            <div class="three_box">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            
                                <br><br><br><br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="box_text">
                                            <h3>Inserir Tabela Nutricional</h3>
                                            <table width="100%">
                                                <tr>
                                                    <th width="50%">Produto</th>
                                                    <th width="50%">Valor Energético Porção (kcal)</th>
                                                </tr>
                                                <tr>
                                                    <td><select name='prod' required>
                                                            <%  String f = "";
                                                                for (Produto c : produtos) {
                                                                    f += "<option value='" + c.getProCodigo() + "'>" + c.getProNome() + "</option>";
                                                                }
                                                                out.println(f);
                                                            %>
                                                        </select></td>
                                                    <td><input name="ve" type="text" required></td>
                                                </tr>
                                                <tr>
                                                    <th width="20%">Porção (g ou ml)</th>
                                                    <th width="20%">Unidade de medida</th>
                                                </tr>
                                                <tr>
                                                    <td><input name="por" type="text" required></td>
                                                    <td><select name='uni' required>                                                               
                                                            <%
                                                                f = "";
                                                                for (UnidadeMedida c : und.consultarUnidadeMedida()) {
                                                                    f += "<option value='" + c.getUndCodigo() + "'>" + c.getUndNome() + "</option>";
                                                                }
                                                                out.println(f);
                                                            %>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <th width="20%">Total  (colheres ou copos)</th>
                                                    <th width="20%">Unidade de medida (colheres ou copos)</th>
                                                </tr>
                                                <tr>
                                                    <td><input name="col" type="text" required></td>
                                                    <td><select name='uni_col' required>                                                               
                                                            <%
                                                                f = "";
                                                                for (UnidadeMedida c : und.consultarUnidadeMedida()) {
                                                                    f += "<option value='" + c.getUndCodigo() + "'>" + c.getUndNome() + "</option>";
                                                                }
                                                                out.println(f);
                                                            %>
                                                        </select></td>
                                                   </tr>
                                           
                                                <tr>
                                                    <th colspan='4' style="background-color:#ced4da">
                                                        Nutrientes 
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th>Nutriente</th>
                                                    <th>Valor por porção (g ou mg)</th>
                                                </tr>
                                                <%
                                                    f = "";
                                                    int i = 0;
                                                    for (Elemento x : ec.consultarElementos()) {
                                                        f += "<tr><td><input type='hidden' name='n" + i + "' value='"+x.getEleCodigo()+"'>";
                                                        f += "<label for='n" + i + "'>" + x.getEleNome() + "</label></td>";
                                                        f += "<td  ><input type='text' name='vp" + i + "'></td>";
                                                        i++;
                                                    }
                                                    out.println(f);
                                                %>

                                                <tr>
                                                    <td colspan='6'>
                                                        <input type="submit" value="Inserir novo produto"> 
                                                    </td>
                                                </tr>
                                                <tr><td colspan='4'>                                                                             
                                                   <a href='inserirTabela.jsp' class='btn btn-secondary btn-sm'>
                                                   <svg class='icon' viewBox='0 0 24 24'>
                                                   <path d='M12 5v14'></path>
                                                   <path d='M5 12h14'></path>
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
                                                   </svg>Imprimir tabelas nutricionais cadastradas</a> 
                                                   </td>
                                               </tr> 
                                            </table>    
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <footer>
            <div class="row">
                <div class="col-md-12">
                    <div id="myCarousel" class="carousel slide testimonial_Carousel " data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                        </ol>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <div class="container">
                                    <div class="carousel-caption ">
                                        <div class="row">
                                            <div class="col-md-15 margin_boot">

                                               <%@include file="rodape.jsp"%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>  
                        </div>    
                    </div>  
                </div> 
            </div>  
        </footer>
        <!-- end footer -->
        <!-- Javascript files-->
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/jquery-3.0.0.min.js"></script>
        <!-- sidebar -->
        <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>