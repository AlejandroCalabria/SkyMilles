<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@page import="modelo.Produto" %>
<%@page import="modelo.Fabricante" %>
<%@page import="controle.ProdutoControle" %>
<%@page import="controle.FabricanteControle" %>
<%@page import="java.util.ArrayList" %>
<%
    ProdutoControle pro = new ProdutoControle();
    FabricanteControle fab = new FabricanteControle();
    ArrayList<Fabricante> fabs = fab.consultarFabricantes();
    String fabricante = request.getParameter("nome");
    if (fabricante != null) {
        Produto ele = new Produto();
        ele.setProNome(request.getParameter("nome"));
        ele.setFabCodigo(Integer.parseInt(request.getParameter("produtor")));
        ele.setProDataFabricacao(request.getParameter("fabricacao"));
        ele.setProDataVencimento(request.getParameter("vencimento"));
        ele.setProPeso(Double.parseDouble(request.getParameter("peso")));
        ele.setProRecomendacoes(request.getParameter("recomendacoes"));
        ele.setProIngredientes(request.getParameter("ingredientes"));
        ele.setProNomeFantasia(request.getParameter("nomeF"));
        pro.inserirProduto(ele);
        
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
        <form action="inserirProduto.jsp" method="POST">
            <div class="three_box">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                           
                                <br><br><br><br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="box_text">
                                            <h3>Inserir novo produto</h3>
                                            <table width="100%">
                                                <tr>
                                                    <th width="20%">Nome</th>
                                                    <th width="20%">Nome Fantasia</th>
                                                    <th width="5%">Produtor</th>
                                                    <th width="5%">Data de Fabricação</th>
                                                    <th width="5%">Data de Vencimento</th>
                                                </tr>
                                                <tr>
                                                    <td><input name="nome" type="text"></td>
                                                    <td><input name="nomeF" type="text"></td>
                                                    <td>    
                                                        <select name='produtor'>
                                                            <%                                                                String f = "";
                                                                for (Fabricante c : fabs) {
                                                                    f += "<option value='" + c.getFabCodigo() + "'>" + c.getFabNome() + "</option>";
                                                                }
                                                                out.println(f);
                                                            %>
                                                        </select>   
                                                    </td>
                                                    <td><input name="fabricacao" type="date"></td>
                                                    <td><input name="vencimento" type="date"></td>
                                                </tr>
                                                <tr>
                                                    <th width="20%" colspan='2'>Ingredientes</th>
                                                    <th width="20%" colspan='2'>Recomendacões</th>                                                    
                                                    <th width="10%">Peso</th>
                                                </tr>
                                                <tr>
                                                    <td colspan='2'><input name="ingredientes" type="text"></td>
                                                    <td colspan='2'><input name="recomendacoes" type="text"></td>
                                                    <td><input name="peso" step="0.01" type="number"></td>
                                                <tr>
                                                    <td colspan='5'>
                                                        <input type="submit" value="Inserir novo produto"> 
                                                    </td>
                                                </tr>
                                                <tr><td colspan='5'>                                                                             
                                                   <a href='inserirProduto.jsp' class='btn btn-secondary btn-sm'>
                                                   <svg class='icon' viewBox='0 0 24 24'>
                                                   <path d='M12 5v14'></path>
                                                   <path d='M5 12h14'></path>
                                                   </svg>Inserir um novo produto</a> 
                                                   <a href='alterarProduto.jsp' class='btn btn-secondary btn-sm'>
                                                   <svg class='icon' viewBox='0 0 24 24'>
                                                   <path d='M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7'></path>
                                                   <path d='M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z'></path>
                                                   </svg>Alterar os dados dos produtos</a> 
                                                   <a href='removerProduto.jsp' class='btn btn-secondary-remove btn-sm'>
                                                   <svg class='icon' viewBox='0 0 24 24'>
                                                   <polyline points='3,6 5,6 21,6'></polyline>
                                                   <path d='M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2'></path>
                                                   </svg>Remover um produto cadastrado</a>
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
        </form>
       <footer>
                                        <div class="testimonial">
                                            <div class="containerP">
                                                <div class="row">
                                                    <div class="col-md-12">

                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div id="myCarousel"
                                                            class="carousel slide testimonial_Carousel "
                                                            data-ride="carousel">
                                                            <ol class="carousel-indicators">
                                                                <li data-target="#myCarousel" data-slide-to="0"
                                                                    class="active"></li>
                                                                <li data-target="#myCarousel" data-slide-to="1"></li>
                                                                <li data-target="#myCarousel" data-slide-to="1"></li>
                                                            </ol>
                                                            <div class="carousel-inner">
                                                                <div class="carousel-item active">
                                                                    <div class="containerP">
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