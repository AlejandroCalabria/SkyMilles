<table style="border-collapse:collapse;width:800px">   
    <tr>
        <td colspan="2" style="background-color:gray"><b>Informações do produto</b></td>
    </tr>    
    <tr>
      <td style="width:500px"><b>Nome do produto</b><br>
      <%
             String nomeProduto = request.getParameter("produto");
             if(nomeProduto != null){
                String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProNome();
                out.println(nome); 
             }
        %>
      </td>
      <td style="width:300px"><b>Marca</b><br>      
        <%
             if(nomeProduto != null){
                String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProNomeFantasia();
                out.println(nome); 
             }
        %>
      </td>
    </tr> 
    <tr>
      <td><b>Ingredientes</b><br>
      <%
             if(nomeProduto != null){
                String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProIngredientes();
                out.println(nome); 
             }
        %>
      </td>
      <td><b>Peso</b><br>
      <%
             if(nomeProduto != null){
                double nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProPeso();
                out.println(Math.round(nome) + " " + unc.consultarUnidadeMedidaCodigo(tabc.consultarTabelaNutricionalCodigoProduto(Integer.parseInt(nomeProduto)).getUndCodigo()).getUndNome());
             }
        %>
      </td>
    </tr>
    <tr>
      <td colspan="2"><b>Recomendações</b><br>
      <%
             if(nomeProduto != null){
                String nome = pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getProRecomendacoes();
                out.println(nome); 
             }
        %>
      </td>
    </tr>
    <tr>
        <td colspan="2" style="background-color:gray"><b>Informações do produtor</b></td>
    </tr>
    <tr>
      <td colspan="2"><b>Nome do agricultor</b><br>
      <%
        if(nomeProduto != null){
            String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabNome();
            out.println(nome); 
        }
      %>
      </td>
    </tr>
    <tr>
      <td style="width:500px"><b>Endereço</b><br>      
        <%
             if(nomeProduto != null){
                String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabEndereco();
                out.println(nome); 
            }
        %>
      </td>
      <td style="width:300px"><b>Contato</b><br>      
        <%
             if(nomeProduto != null){
                String nome = fab.consultarFabricanteCodigo(pro.consultarProdutoCodigo(Integer.parseInt(nomeProduto)).getFabCodigo()).getFabContato();
                out.println(nome); 
            }
        %>
      </td>
    </tr>
    <tr>
        <td colspan="2" style="background-color:gray"><b>Informações nutricionais</b></td>
    </tr>
    <tr>
        <td colspan="2"><%@include file="montarTabela.jsp"%></td>
    </tr>
    
</table>