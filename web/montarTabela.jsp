<%@page import="controle.ElementoControle"%>
<%@page import="controle.UnidadeMedidaControle"%>
<%@page import="modelo.TabNutElemento"%>
<style>
        .tableN, .thN, .tdN {
            border: 1px solid black;
            border-collapse: collapse;
        }
        .thN, .tdN {
              padding: 2px;
        }
</style>   
<table border=1 style="color:#000000;width:60%;font-size:11pt;font-family:'Calibri';margin-left:3px" class="tableN" >
    
    <tr style="background-color:#ffffff;text-align:center">
        <td colspan="4" class="tdN">
            <center><b>INFORMAÇÃO NUTRICIONAL</b></center>
        </td>    
    </tr> 
    <tr style="background-color:#ffffff">
        <td colspan="4"  class="tdN">
            <%
                nomeProduto = request.getParameter("produto");
                if(nomeProduto != null){
                   tabnute = tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto));
                   out.println("Porções por embalagem: " + tabnute.getTabTotalPorcao());
                   int totalMedida = tabnute.getTabTotalColheres();
                   String colheres = "";
                   if (totalMedida == 1)
                        colheres = unc.consultarUnidadeMedidaCodigo(tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto)).getTabUnidadeMedidasColheres()).getUndNome();
                   else{
                        if(totalMedida == 3)
                            colheres = "colheres de sopa";
                        else if(totalMedida == 4)
                            colheres = "colheres de sobremesa";
                        else if (totalMedida == 11)  
                            colheres = "copos";
                    }   
                    out.println("<br>Porção: " + tabnute.getTabPorcao() + " " + unc.consultarUnidadeMedidaCodigo(tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto)).getUndCodigo()).getUndNome() + " (" + totalMedida + " " + colheres + ")"); 
                }
            %>
        </td>    
    </tr> 
    <tr style="background-color:#ffffff;text-align:center;padding:0px;">
        <td width="60%"  class="tdN"></td> 
        <td width="20%" class="tdN"><b><%
            if(nomeProduto != null){
               tabnute = tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto));
               out.println(Math.round(tabnute.getTabPorcaoPadrao()) + " " + unc.consultarUnidadeMedidaCodigo(tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto)).getUndCodigo()).getUndNome());
            }
            %></td></b>
        <td width="15%" class="tdN"><b><%
            if(nomeProduto != null){
               //tabnute = tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto));
               out.println(Math.round(tabnute.getTabPorcao()) + " " + unc.consultarUnidadeMedidaCodigo(tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto)).getUndCodigo()).getUndNome());
            }%></td>
        <td width="15%" class="tdN"><b>%VD<sup>*</sup></b></td>
    </tr>
    <tr style="background-color:#ffffff;text-align:center;">
        <td width="60%" style="text-align:left;" class="tdN">Calorias (kcal)</td> 
        <td width="20%" class="tdN" style='text-align:center;'><%
            if(nomeProduto != null){
               //tabnute = tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto));
               out.println(tabnute.getTabValorEnergetico());
            }
            %></td>
        <td width="15%" class="tdN" style='text-align:center;'><%
            if(nomeProduto != null){
               //tabnute = tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto));
               out.println(tabnute.getTabValorEnergeticoPorcao());
            }%></td>
        <td width="15%" class="tdN" style='text-align:center;'><%
            if(nomeProduto != null){
               //tabnute = tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto));
               out.println(tabnute.getTabVD());
            }%></td>
    </tr>
    <%
        nomeProduto = request.getParameter("produto");
        if(nomeProduto != null){
           
           tabnute = tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto));
           ArrayList<TabNutElemento> vet = tabnute.getTneElementos();
           ElementoControle elecon = new ElementoControle();
           String z = "";
           for(int i = 0; i < vet.size(); i++){
                if(vet.get(i).getEleCodigo() != 2)
                   z = z+vet.get(i).getTneVD();
                out.println("<tr style='background-color:#ffffff;'>");
                out.println("<td class='tdN' style='text-align:left;'>" + elecon.consultarElementoCodigo(vet.get(i).getEleCodigo()).getEleNome() + "</td>");
                out.println("<td class='tdN' style='text-align:center;'>" + vet.get(i).getTneValorPadrao() + "</td>");   
                out.println("<td class='tdN' style='text-align:center;'>" + vet.get(i).getTneValor() + "</td>");   
                out.println("<td class='tdN' style='text-align:center;'>" + z + "</td></tr>"); 
                z = "";
        }     
        }%>
    <tr style="background-color:#ffffff;text-align:left">
        <td colspan="4" class="tdN">
            <sup>*</sup> Percentual de valores diários fornecidos pela porção
        </td>    
    </tr>
</table>    
