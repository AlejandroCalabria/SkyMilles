 <td style="margin-right:4px">  
        <div name="validade" class="validade">
             <b>Data de fabricação:</b><br>
             <b>Data de Validade: </b>
        </div>  
                    <div name="whatsapp" class="whatsapp">
                         <img src="./images/whatsapp.png" width="25%">
                         <b><%
if(nomeProduto != null){
    String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabContato();
    out.println(nome); 
}%> </b>
                    </div>
                 </td>
              </tr>
              <tr style="background-color:<% 
     if(cor != null)
         out.println(cor); 
%>">
                  <td colspan="2">
                      <div name="nomeProduto" class="nomeProduto"   width="100%"><%
     if(nomeProduto != null){
         String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProNome().toUpperCase();
         out.println(nome); 
     }
%>
                      </div>
                  </td> 
              </tr>
              <tr style="background-color:<% 
     if(cor != null)
         out.println(cor); 
%>;padding:2px" width="100%">
                  <td width="60%" rowspan="4" style="padding:0px;">
<%@include file="montarTabela.jsp"%>
                  </td>
                  <td width="40%"><div width="100%" style="text-align:center;color:#000000;font-size:14pt;font-family:'Calibri'"width="100%"><%
         if(nomeProduto != null){
             String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProRecomendacoes();
             out.println(nome); 
         }
%></div></td>
                  </tr>
               <tr style="background-color:<% 
    if(cor != null)
        out.println(cor); 
%>"><td width="40%"><div style="text-align:center;color:#000000;font-size:14pt;font-family:'Calibri'" width="100%">
                              <h3 style="text-decoration:underline;text-align:center;display:block;">INGREDIENTES</h3>
<%
if(nomeProduto != null){
 String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProIngredientes();
 out.println(nome); 
}
%></div></td></tr>
               <tr style="background-color:<% 
    if(cor != null)
        out.println(cor); 
%>"><td width="40%"><div style="text-align:center;color:#000000;font-size:20pt;font-family:'Calibri'" width="100%">
                              <b><%
if(nomeProduto != null){
 double nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProPeso();
 out.println(Math.round(nome) + " " + unc.consultarUnidadeMedidaCodigo(tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto)).getUndCodigo()).getUndNome()); 
                               
}
%></b></div></td></tr>
                  <tr style="background-color:<% 
 if(cor != null)
     out.println(cor); 
%>"><td width="40%"><div style="text-align:center;color:#000000;font-size:14pt;font-family:'Calibri'" width="100%"><% out.println(request.getParameter("gluten")); %></div></td></tr>
               <tr style="background-color:<% 
    if(cor != null)
        out.println(cor); 
%>"><td width="40%" colspan="2">
                    <table>
                        <tr>
                            <td width="70%"><div style="text-align:center;color:#000000;font-size:14pt;font-family:'Calibri'" width="100%">
                               <p style="text-align:center;display:block;"><b>Produzido por: </b> 
<%
if(nomeProduto != null){
    String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabNome();
    out.println(nome); 
}%>
                              </p>
                              <p style="text-align:center;display:block;margin:0"><b>Endereço: </b> 
<%
  if(nomeProduto != null){
      String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabEndereco();
      out.println(nome); 
  }%>
                              </p>
                              </div>
                            </td>  
                            <td width="30%"><div style="color:#000000;font-size:14pt;font-family:'Calibri'" width="100%">
                                <img src="./images/feira.png" width="70%"><img src="./images/cieps.png" width="70%">
                                </div>
                            </td>    
                        </tr>
                    </table>
                </td></tr>
            </table></center>
        </td>
    </tr>    
</table>
<br><br>
<table  style="border-collapse:collapse;width:950px;background-color:<% 
                    cor = request.getParameter("fundo");
                    if(cor != null)
                        out.println(cor); 
%>">   

              <tr>
                <td>
                   <div name="titulo" class="titulo" style="height:30px;margin-left:2px;text-align:left;transform: rotate(341deg);">
<%
     nomeProduto = request.getParameter("produto");
     if(nomeProduto != null){
        String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProNomeFantasia();
        out.println(nome); 
     }
%>
                       
                   </div>
                </td> 
                <td style="margin-right:4px">  
                    <div name="validade" class="validade">
                         <b>Data de fabricação:</b><br>
                         <b>Data de Validade: </b>
                    </div>  
                    <div name="whatsapp" class="whatsapp">
                         <img src="./images/whatsapp.png" width="25%">
                         <b><%
if(nomeProduto != null){
    String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabContato();
    out.println(nome); 
}%> </b>
                    </div>
                 </td>
              </tr>
              <tr style="background-color:<% 
     if(cor != null)
         out.println(cor); 
%>">
                  <td colspan="2">
                      <div name="nomeProduto" class="nomeProduto"   width="100%"><%
     if(nomeProduto != null){
         String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProNome().toUpperCase();
         out.println(nome); 
     }
%>
                      </div>
                  </td> 
              </tr>
              <tr style="background-color:<% 
     if(cor != null)
         out.println(cor); 
%>;padding:1px" width="100%">
                  <td width="50%" rowspan="6" style="padding:0px;">
<%@include file="montarTabela.jsp"%>
                  </td>
                  <td width="40%"><div width="100%" style="text-align:center;color:#000000;font-size:14pt;font-family:'Calibri'"width="100%"><%
         if(nomeProduto != null){
             String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProRecomendacoes();
             out.println(nome); 
         }
%></div></td>
                  </tr>
               <tr style="background-color:<% 
    if(cor != null)
        out.println(cor); 
%>"><td width="40%"><div style="text-align:center;color:#000000;font-size:14pt;font-family:'Calibri'" width="100%">
                              <h3 style="text-decoration:underline;text-align:center;display:block;">INGREDIENTES</h3>
<%
if(nomeProduto != null){
 String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProIngredientes();
 out.println(nome); 
}
%></div></td></tr>
               <tr style="background-color:<% 
    if(cor != null)
        out.println(cor); 
%>"><td width="40%"><div style="text-align:center;color:#000000;font-size:20pt;font-family:'Calibri'" width="100%">
                              <b><%
if(nomeProduto != null){
 double nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProPeso();
 out.println(Math.round(nome) + " " + unc.consultarUnidadeMedidaCodigo(tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto)).getUndCodigo()).getUndNome()); 
                               
}
%></b></div></td></tr>
                  <tr style="background-color:<% 
 if(cor != null)
     out.println(cor); 
%>"><td width="40%"><div style="text-align:center;color:#000000;font-size:14pt;font-family:'Calibri'" width="100%"><% out.println(request.getParameter("gluten")); %></div></td></tr>
               <tr style="background-color:<% 
    if(cor != null)
        out.println(cor); 
%>"><td width="40%" colspan="2">
                    <table>
                        <tr>
                            <td width="70%"><div style="text-align:center;color:#000000;font-size:14pt;font-family:'Calibri'" width="100%">
                               <p style="text-align:center;display:block;"><b>Produzido por: </b> 
<%
if(nomeProduto != null){
    String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabNome();
    out.println(nome); 
}%>
                              </p>
                              <p style="text-align:center;display:block;margin:0"><b>Endereço: </b> 
<%
  if(nomeProduto != null){
      String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabEndereco();
      out.println(nome); 
  }%>
                              </p>
                              </div>
                            </td> 
           
                            <td width="30%"><div style="text-align:left;color:#000000;font-size:14pt;font-family:'Calibri'" width="100%">
                                <img src="./images/feira.png" width="150%"><img src="./images/cieps.png" width="150%">
                                </div>
                            </td>    
                        </tr>
                    </table>
                </td></tr>
            </table></center>
        </td>
    </tr>    