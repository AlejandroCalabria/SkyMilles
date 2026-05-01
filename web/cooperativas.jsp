<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@page import="modelo.Fabricante" %>
<%@page import="controle.FabricanteControle" %>
<%@page import="java.util.ArrayList" %>
<%
    FabricanteControle con = new FabricanteControle();
    ArrayList<Fabricante> res = con.consultarFabricantes();
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
        <div class="three_box">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box_text">
                            <h3><font color="#A0522D">Gerenciamento de Produtores</font></h3>
                            <br><br><br><br>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="box_text">
                                        <table width="100%">
                                            <tr>
                                                <th width="5%">Códigos</th>
                                                <th width="20%">Produtores</th>
                                                <th width="50%">Endereço</th>
                                                <th width="25%">Cooperativa</th>
                                                
                                            </tr>
                                            <%
                                                String linha = "";
                                                for (Fabricante c : res) {
                                                    linha += "<tr><td>" + c.getFabCodigo() + "</td>";
                                                    linha += "<td>" + c.getFabNome() + "</td>";
                                                    linha += "<td>" + c.getFabEndereco()+ "</td>";
                                                    linha += "<td>" + c.getFabCooperativa()+ "</td></tr>";
                                                }
                                                linha += "<tr><td colspan=4 ><a href='inserirProdutor.jsp'>Inserir novo produtor</a>   |   <a href='alterarProdutor.jsp'>Alterar dados dos produtores</a>   |   <a href='removerProdutor.jsp'>Remover produtores cadastrados</a></td></tr>";
                                                out.println(linha);
                                            %>
                                        </table>    
                                    </div>
                                </div>
                            </div>
                                        
                                        <br><br><br><br>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="box_text">
                                        <table width="100%">
                                            <tr>
                                                <th>Gerenciamento de Cooperativas</th>
                                                
                                                
                                            </tr>
                                            <%
                                                String linha2 = "";
                                                
                                                linha2 += "<tr><td colspan=4 ><a href='inserirCooperativa.jsp'>Inserir nova cooperativa</a>   |   <a href='alterarCooperativa.jsp'>Alterar dados das cooperativas</a>   |   <a href='removerCooperativa.jsp'>Remover cooperativas cadastradas</a></td></tr>";
                                                out.println(linha2);
                                            %>
                                        </table>    
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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