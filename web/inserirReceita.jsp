<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Receita"%>
<%@page import="modelo.IngredienteReceita"%>
<%@page import="modelo.ValoresNutricionais"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    String acao = request.getParameter("acao");
    String nomeProduto = "";
    double porcao = 0;
    String unidade = "g";
    int numIngredientes = 1;

    if (("calcular").equals(acao)) {
        nomeProduto = request.getParameter("nomeProduto");
        String pParam = request.getParameter("porcao");
        if (pParam != null && !pParam.isEmpty()) {
            porcao = Double.parseDouble(pParam);
        }
        String gParam = request.getParameter("g_ml");
        if (gParam != null && !gParam.isEmpty()) {
            unidade = gParam;
        }
        String nParam = request.getParameter("numIngredientes");
        if (nParam != null && !nParam.isEmpty()) {
            numIngredientes = Integer.parseInt(nParam);
        }
    }
%>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Nutri IFTM - Calculadora TACO</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/responsive.css">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <style>
        .loader_bg { position: fixed; z-index: 999999; width: 100%; height: 100%; background: #fff; display: flex; align-items: center; justify-content: center; }
        .loader_bg .loader { text-align: center; }
        .resultado-section { background: #fff; border: 1px solid #ddd; border-radius: 8px; padding: 8px; margin-top: 20px; }
        .ingrediente-encontrado { color: green; font-size: 0.9em; }
        .ingrediente-nao-encontrado { color: red; font-size: 0.9em; }
        .ingrediente-sugerido { color: #0066aa; font-size: 0.85em; }
        .rotulo-preview {
            border: 2px solid #333; padding: 12px; margin-top: 10px;
            font-family: Arial, Helvetica, sans-serif; max-width: 500px; background: #fff;
        }
        .rotulo-preview .titulo-info {
            font-size: 12pt; font-weight: bold; text-align: center;
            border-bottom: 3px solid #333; padding-bottom: 4px; margin-bottom: 6px;
        }
        .rotulo-preview .info-produto {
            font-size: 9pt; margin-bottom: 4px;
            border-bottom: 3px solid #333; padding-bottom: 4px;
        }
        .tabela-anvisa { border-collapse: collapse; width: 100%; border-top: 2px solid #333; }
        .tabela-anvisa td, .tabela-anvisa th { border-top: 2px solid #333; padding: 2px 4px; font-size: 9pt; }
        .tabela-anvisa td:last-child, .tabela-anvisa th:last-child { text-align: right; }
        .rotulo-preview .nota { font-size: 7pt; margin-top: 6px; text-align: center; border-top: 3px solid #333; padding-top: 4px; }
        .alerta-frontal { color: #cc0000; font-weight: bold; background: #fff3f3; border: 1px solid #cc0000; padding: 8px; border-radius: 4px; margin-top: 10px; }
        .autocomplete-list { position: absolute; background: #fff; border: 1px solid #ccc; border-radius: 0 0 4px 4px; max-height: 200px; overflow-y: auto; z-index: 1000; min-width: 300px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); display: none; }
        .autocomplete-item { padding: 8px 12px; cursor: pointer; border-bottom: 1px solid #f0f0f0; font-size: 13px; }
        .autocomplete-item:hover, .autocomplete-item.active { background-color: #007bff; color: #fff; }
        .autocomplete-item .ac-cat { color: #999; font-size: 11px; margin-left: 8px; }
        .autocomplete-item.active .ac-cat { color: #ddd; }
        .autocomplete-container { position: relative; }
        .rotulo-preview-wrapper { display: flex; flex-direction: column; align-items: center; margin-top: 15px; }
        .rotulo-preview { min-width: 400px; max-width: 500px; }
        .edit-val { border: 1px solid #999; padding: 1px 4px; font-size: inherit; background: #fffff0; text-align: center; }
        .edit-val:focus { outline: 2px solid #007bff; background: #fff; }
        .lupa { color: #ff0000; font-size: 1.1em; font-weight: bold; }
    </style>
</head>

<body class="main-layout">
    <div class="loader_bg" style="display:none;"><div class="loader"><img src="images/loading.gif" alt="#"/></div></div>
    <%@include file="menu.jsp"%>

    <div class="three_box">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="box_text">
                        <h3>Calculadora Nutricional por Receita (Tabela TACO)</h3>
                        <p>Insira ingredientes e quantidades. Os dados da TACO sao usados para gerar o rotulo conforme ANVISA.</p>

                        <form action="inserirReceita.jsp" method="POST" id="formReceita" autocomplete="off" accept-charset="UTF-8"<% if (acao != null) { %> style="display:none"<% } %>>
                            <table width="100%">
                                <tr>
                                    <th width="60%">Nome do Produto</th>
                                    <th width="40%">Tamanho da porcao (g ou ml)</th>
                                </tr>
                                <tr>
                                    <td><input name="nomeProduto" type="text" required placeholder="Ex: Molho de Pimenta"></td>
                                    <td style="display:flex; gap:4px;">
                                        <input name="porcao" type="number" step="0.01" min="1" required placeholder="30" style="flex:1;">
                                        <select name="g_ml" style="width:60px;">
                                            <option value="g" selected>g</option>
                                            <option value="ml">ml</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr><th colspan="2">Ingredientes (busca na TACO)</th></tr>
                                <tbody id="ingredientes-container">
                                    <tr class="ingrediente-row" id="ing-row-0">
                                        <td>
                                            <div class="autocomplete-container">
                                                <input name="ingrediente_0" type="text" id="ingrediente_0" placeholder="Digite para buscar..." required style="width:100%;" autocomplete="off">
                                                <div id="autocomplete_0" class="autocomplete-list"></div>
                                            </div>
                                        </td>
                                        <td>
                                            <input name="qtd_0" type="number" step="0.1" min="0.1" class="qtd-input" placeholder="Gramas" required style="width:100%;" autocomplete="off">
                                            <div id="match_0" class="ingrediente-sugerido"></div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tr>
                                    <td colspan="2" style="background:none;">
                                        <button type="button" class="btn btn-info btn-sm" onclick="adicionarIngrediente()" style="margin-top:5px;">
                                            + Adicionar Ingrediente
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="background:none;padding-top:10px;">
                                        <input type="hidden" name="acao" value="calcular">
                                        <input type="hidden" name="numIngredientes" id="numIngredientes" value="1">
                                        <input type="submit" value="Calcular Tabela Nutricional" class="btn btn-primary">
                                    </td>
                                </tr>
                            </table>
                        </form>

<%
if (acao != null) {
    ArrayList<String> encontrados = new ArrayList<>();
    ArrayList<modelo.SugestaoIngrediente> sugestoesList = new ArrayList<>();

    modelo.TACOService svc = new modelo.TACOService();
    modelo.Receita receita = new modelo.Receita();
    receita.setPorcaoG(porcao);

    for (int i = 0; i < numIngredientes; i++) {
        String nomeIng = request.getParameter("ingrediente_" + i);
        if (nomeIng == null || nomeIng.trim().isEmpty()) continue;
        double qtd = 0;
        try { qtd = Double.parseDouble(request.getParameter("qtd_" + i)); }
        catch (Exception ex) { qtd = 0; }

        modelo.IngredienteReceita ing = new modelo.IngredienteReceita(nomeIng, qtd);
        modelo.ItemTACO encontradoItem = svc.buscarPorNome(nomeIng);

        if (encontradoItem != null) {
            ing.setItemTACO(encontradoItem);
            encontrados.add(nomeIng + " => " + encontradoItem.getDescricao() + " (" + qtd + "g, " + encontradoItem.getEnergia() + "kcal/100g)");
        } else {
            java.util.List<modelo.ItemTACO> su = svc.buscarSugestoes(nomeIng, 3);
            sugestoesList.add(new modelo.SugestaoIngrediente(nomeIng, su));
        }
        receita.adicionarIngrediente(ing);
    }

    modelo.MacronutrientesCalc calc = new modelo.MacronutrientesCalc();
    modelo.ValoresNutricionais v = calc.calcular(receita);

    String fPT = String.format(Locale.US, "%.1f", receita.getPesoTotalG());
    String fPO = String.format(Locale.US, "%.1f", porcao);

    boolean altoAcucar = v.getCarboidratoPorcao() > 15;
    boolean altoSat = v.getSaturadoPorcao() > 4;
    boolean altoSodio = v.getSodioPorcao() > 300;
%>
                        <div class="resultado-section">
                            <h4>Resultado</h4>
<%
if (!encontrados.isEmpty()) {
    // mostra detalhes em detalhes (colapsado)
    out.println("<details><summary style='cursor:pointer;color:#007bff;font-size:13px;'><b>Ingredientes processados (" + encontrados.size() + " encontrados)</b></summary><ul>");
    for (String e : encontrados) {
        out.println("<li class='ingrediente-encontrado'>" + e + "</li>");
    }
    out.println("</ul></details>");
}
if (!sugestoesList.isEmpty()) {
    out.println("<details style='margin-top:8px;'><summary style='cursor:pointer;color:#b45309;font-size:13px;'><b>Ingredientes NAO encontrados (" + sugestoesList.size() + ")</b></summary><ul>");
    for (modelo.SugestaoIngrediente s : sugestoesList) {
        out.println("<li class='ingrediente-nao-encontrado'>" + s.nomeBusca + "</li>");
        if (s.sugestoes != null && !s.sugestoes.isEmpty()) {
            out.println("<ul>");
            for (modelo.ItemTACO sug : s.sugestoes) {
                out.println("<li class='ingrediente-sugerido'>Tente: <b>" + sug.getDescricao() + "</b> [" + sug.getCategoria() + "] - " + sug.getEnergia() + " kcal/100g</li>");
            }
            out.println("</ul>");
        }
    }
    out.println("</ul></details>");
}

String lup = "<span class='lupa' title='Nutriente em quantidade elevada (IN 75/2020)'>&#128269;</span>";

// Montar resumo de ingredientes como string
String ingredientesResumo = "";
boolean first = true;
for (String eng : encontrados) {
    if (!first) ingredientesResumo += ", ";
    // Pega so a descricao, remove o match TACO
    int idx = eng.indexOf(" => ");
    if (idx > 0) {
        ingredientesResumo += eng.substring(idx + 4);
    } else {
        ingredientesResumo += eng;
    }
    first = false;
}
%>
                        <p><b>Produto:</b> <%= nomeProduto %> | <b>Peso total:</b> <%= fPT %>g | <b>Porcão:</b> <%= fPO %>g | <b>Porções por embalagem:</b> <span id="tPorcoes"><%= String.valueOf(receita.getTotalPorcoes()) %></span></p>

                        <!-- Rotulo Nutricional Centralizado e Editavel -->
                        <div class="rotulo-preview-wrapper">
                            <form method="POST" action="salvarReceita.jsp" id="formSalvar">
                                <input type="hidden" name="produto" id="f_produto" value="<%= nomeProduto %>">
                                <input type="hidden" name="porcao" id="f_porcao" value="<%= fPO %>">
                                <input type="hidden" name="unidade" id="f_unidade" value="<%= unidade %>">
                                <input type="hidden" name="pesoTotal" id="f_pesoTotal" value="<%= String.format(Locale.US, "%.2f", receita.getPesoTotalG()) %>">
                                <input type="hidden" name="energia" id="f_energia" value="<%= String.format(Locale.US, "%.1f", v.getEnergiaPorcao()) %>">
                                <input type="hidden" name="carboidrato" id="f_carboidrato" value="<%= String.format(Locale.US, "%.1f", v.getCarboidratoPorcao()) %>">
                                <input type="hidden" name="proteina" id="f_proteina" value="<%= String.format(Locale.US, "%.1f", v.getProteinaPorcao()) %>">
                                <input type="hidden" name="lipideos" id="f_lipideos" value="<%= String.format(Locale.US, "%.1f", v.getLipideosPorcao()) %>">
                                <input type="hidden" name="saturado" id="f_saturado" value="<%= String.format(Locale.US, "%.1f", v.getSaturadoPorcao()) %>">
                                <input type="hidden" name="fibra" id="f_fibra" value="<%= String.format(Locale.US, "%.1f", v.getFibraPorcao()) %>">
                                <input type="hidden" name="sodio" id="f_sodio" value="<%= String.format(Locale.US, "%.1f", v.getSodioPorcao()) %>">
                                <input type="hidden" name="trans" id="f_trans" value="0">
                                <input type="hidden" name="ingredientes" id="f_ingredientes" value="<%= ingredientesResumo %>">

                            <!-- Seção de informações adicionais do produto -->
                            <div style="background:#f8f9fa; border:1px solid #ddd; border-radius:6px; padding:15px; max-width:550px; margin-bottom:15px;">
                                <h4 style="margin-top:0; font-size:15px;">Informações do Produto</h4>
                                <table width="100%">
                                    <tr>
                                        <td><b>Fabricante:</b><br>
                                            <select name="fabricante" style="width:100%; padding:4px;">
                                                <% controle.FabricanteControle fabCtrlTemp = new controle.FabricanteControle();
                                                   modelo.Fabricante fabDefault = null;
                                                   for (modelo.Fabricante ff : fabCtrlTemp.consultarFabricantes()) {
                                                       if (ff.getFabNome().contains("Teste") && fabDefault == null) fabDefault = ff;
                                                   } %>
                                                <% for (modelo.Fabricante ff : fabCtrlTemp.consultarFabricantes()) {
                                                       String sel = (fabDefault != null && ff.getFabCodigo() == fabDefault.getFabCodigo()) ? "selected" : "";
                                                   %>
                                                    <option value="<%= ff.getFabCodigo() %>" <%=sel%>><%= ff.getFabNome() %></option>
                                                <% } %>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><b>Título (Nome Fantasia):</b><br>
                                            <input type="text" name="nomeFantasia" style="width:100%; padding:4px;" placeholder="Nome no rótulo">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="display:flex; gap:10px;">
                                            <div style="flex:1;">
                                                <b>Fabricação:</b><br>
                                                <input type="date" name="dataFabricacao" style="width:100%; padding:4px;" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                                            </div>
                                            <div style="flex:1;">
                                                <b>Vencimento:</b><br>
                                                <input type="date" name="dataVencimento" style="width:100%; padding:4px;">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><b>Ingredientes:</b><br>
                                            <textarea name="ingredientes" rows="3" style="width:100%;" placeholder="Ingredientes do produto..."><%= ingredientesResumo != null ? ingredientesResumo : "" %></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><b>Recomendações:</b><br>
                                            <textarea name="recomendacoes" rows="2" style="width:100%;" placeholder="Recomendações de armazenamento..."></textarea>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                                <div class="rotulo-preview">
                                    <div class="titulo-info">INFORMACAO NUTRICIONAL</div>
                                    <div class="info-produto">
                                        Porcão: <input type="text" class="edit-val rot-porcao" id="ed_porcao" value="<%= fPO %>" style="width:60px;"> g
                                        (<span id="ed_porcoes"><%= String.valueOf(receita.getTotalPorcoes()) %></span> porções por embalagem)
                                    </div>
                                    <div class="info-produto" style="font-size:8pt;">Produto: <input type="text" class="edit-val" id="ed_nome" value="<%= nomeProduto %>" style="width:200px; font-size:inherit;"></div>

                                    <table class="tabela-anvisa">
                                        <tr>
                                            <th></th>
                                            <th>Por 100g</th>
                                            <th>Por porçāo</th>
                                            <th>%%VD</th>
                                        </tr>
                                        <tr>
                                            <td><b>Valor energético (kcal)</b></td>
                                            <td><%= String.format("%.1f", v.getEnergia100g()) %></td>
                                            <td><input type="text" class="edit-val" id="ed_energia" value="<%= String.format("%.1f", v.getEnergiaPorcao()) %>" data-por100="<%= v.getEnergia100g() %>" oninput="atualizarPorcentagem(this, 'p_energy')" style="width:65px;"></td>
                                            <td id="p_energy"><%= String.format("%.1f", v.getVDEnergia()) %></td>
                                        </tr>
                                        <tr>
                                            <td><b>Carboidratos (g)</b></td>
                                            <td><%= String.format("%.1f", v.getCarboidrato100g()) %></td>
                                            <td><input type="text" class="edit-val" id="ed_carboidrato" value="<%= String.format("%.1f", v.getCarboidratoPorcao()) %>" data-por100="<%= v.getCarboidrato100g() %>" oninput="atualizarPorcentagem(this, 'p_carb')" style="width:65px;"> <%= altoAcucar ? lup : "" %></td>
                                            <td id="p_carb"><%= String.format("%.1f", v.getVDCarboidrato()) %></td>
                                        </tr>
                                        <tr>
                                            <td><b>Proteínas (g)</b></td>
                                            <td><%= String.format("%.1f", v.getProteina100g()) %></td>
                                            <td><input type="text" class="edit-val" id="ed_proteina" value="<%= String.format("%.1f", v.getProteinaPorcao()) %>" data-por100="<%= v.getProteina100g() %>" oninput="atualizarPorcentagem(this, 'p_prot')" style="width:65px;"></td>
                                            <td id="p_prot"><%= String.format("%.1f", v.getVDProteina()) %></td>
                                        </tr>
                                        <tr>
                                            <td><b>Gorduras Totais (g)</b></td>
                                            <td><%= String.format("%.1f", v.getLipideos100g()) %></td>
                                            <td><input type="text" class="edit-val" id="ed_lipideos" value="<%= String.format("%.1f", v.getLipideosPorcao()) %>" data-por100="<%= v.getLipideos100g() %>" oninput="atualizarPorcentagem(this, 'p_lip')" style="width:65px;"></td>
                                            <td id="p_lip"><%= String.format("%.1f", v.getVDLipideos()) %></td>
                                        </tr>
                                        <tr>
                                            <td><b>Gorduras Saturadas (g)</b></td>
                                            <td><%= String.format("%.1f", v.getSaturado100g()) %></td>
                                            <td><input type="text" class="edit-val" id="ed_saturado" value="<%= String.format("%.1f", v.getSaturadoPorcao()) %>" data-por100="<%= v.getSaturado100g() %>" oninput="atualizarPorcentagem(this, 'p_sat')" style="width:65px;"> <%= altoSat ? lup : "" %></td>
                                            <td id="p_sat"><%= String.format("%.1f", v.getVDSaturado()) %></td>
                                        </tr>
                                        <tr>
                                            <td><b>Gorduras Trans (g)</b></td>
                                            <td>0</td>
                                            <td><input type="text" class="edit-val" id="ed_trans" value="0" data-por100="0" style="width:65px;"></td>
                                            <td>-</td>
                                        </tr>
                                        <tr>
                                            <td><b>Fibra Alimentar (g)</b></td>
                                            <td><%= String.format("%.1f", v.getFibra100g()) %></td>
                                            <td><input type="text" class="edit-val" id="ed_fibra" value="<%= String.format("%.1f", v.getFibraPorcao()) %>" data-por100="<%= v.getFibra100g() %>" oninput="atualizarPorcentagem(this, 'p_fib')" style="width:65px;"></td>
                                            <td id="p_fib"><%= String.format("%.1f", v.getVDFibra()) %></td>
                                        </tr>
                                        <tr>
                                            <td><b>Sódio (mg)</b></td>
                                            <td><%= String.format("%.1f", v.getSodio100g()) %></td>
                                            <td><input type="text" class="edit-val" id="ed_sodio" value="<%= String.format("%.1f", v.getSodioPorcao()) %>" data-por100="<%= v.getSodio100g() %>" oninput="atualizarPorcentagem(this, 'p_sod')" style="width:65px;"> <%= altoSodio ? lup : "" %></td>
                                            <td id="p_sod"><%= String.format("%.1f", v.getVDSodio()) %></td>
                                        </tr>
                                    </table>
                                    <p class="nota">* Porcentagem dos Valores Diários com base em dieta de 2.000 kcal. Seus valores podem variar conforme suas necessidades energéticas.</p>
                                    <p class="nota"><%= altoAcucar || altoSat || altoSodio ? "Rotulagem frontal (IN 75/2020):" : "" %> <%= altoAcucar ? "&#128269; ACUCARES" : "" %> <%= altoSat ? "&#128269; GORDURA SATURADA" : "" %> <%= altoSodio ? "&#128269; SODIO" : "" %></p>
                                </div>

                            </form>
                            <br>
                            <button type="button" class="btn btn-success" onclick="prepararESalvar()">Salvar no banco de dados</button>
                        </div>
                        </div>
<%
}
%>
                        <br>
                        <a href='index.html' class='btn btn-secondary btn-sm'>Voltar</a>
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
    <script>
        // Fallback inline - esconder loader
        window.addEventListener('load', function() {
            setTimeout(function() {
                var loader = document.querySelector('.loader_bg');
                if (loader) loader.style.display = 'none';
            }, 1000);
        });
    </script>
    <script>
        // Valores Diarios RDC 360/2003
        var VD = { energia: 2000, carboidrato: 300, proteina: 75, lipideos: 55, saturado: 22, fibra: 25, sodio: 2400 };

        function atualizarPorcentagem(input, tdId) {
            var val = parseFloat(input.value) || 0;
            var por100 = parseFloat(input.dataset.por100) || 1;
            var porcao = parseFloat(document.getElementById('ed_porcao').value) || 30;
            // %VD = (valor_por_pocao / VD) * 100
            var vdKey = getVDKey(input.id);
            var pct = 0;
            if (vdKey && VD[vdKey]) pct = (val / VD[vdKey]) * 100;
            document.getElementById(tdId).textContent = pct.toFixed(1);
        }

        function getVDKey(inputId) {
            if (inputId === 'ed_energia') return 'energia';
            if (inputId === 'ed_carboidrato') return 'carboidrato';
            if (inputId === 'ed_proteina') return 'proteina';
            if (inputId === 'ed_lipideos') return 'lipideos';
            if (inputId === 'ed_saturado') return 'saturado';
            if (inputId === 'ed_fibra') return 'fibra';
            if (inputId === 'ed_sodio') return 'sodio';
            return null;
        }

        function prepararESalvar() {
            var porcao = parseFloat(document.getElementById('ed_porcao').value) || 30;
            var pesoTotal = parseFloat(document.getElementById('f_pesoTotal').value) || 150;

            // Recalcula porcoes com base na porcao editada
            var porcoes = porcao > 0 ? pesoTotal / porcao : 0;
            document.getElementById('ed_porcoes').textContent = porcoes.toFixed(0);

            document.getElementById('f_porcao').value = porcao.toFixed(1);
            document.getElementById('f_energia').value = document.getElementById('ed_energia').value;
            document.getElementById('f_carboidrato').value = document.getElementById('ed_carboidrato').value;
            document.getElementById('f_proteina').value = document.getElementById('ed_proteina').value;
            document.getElementById('f_lipideos').value = document.getElementById('ed_lipideos').value;
            document.getElementById('f_saturado').value = document.getElementById('ed_saturado').value;
            document.getElementById('f_fibra').value = document.getElementById('ed_fibra').value;
            document.getElementById('f_sodio').value = document.getElementById('ed_sodio').value;
            document.getElementById('f_produto').value = document.getElementById('ed_nome').value;

            document.getElementById('formSalvar').submit();
        }

        var tacoData = [];
        var ingredienteCount = 1;

        // Carrega dados TACO para autocomplete
        $.getJSON('js/taco_data.json', function(data) {
            tacoData = data;
        }).fail(function() { console.warn('Erro ao carregar taco_data.json'); });

        function adicionarIngrediente() {
            var container = document.getElementById('ingredientes-container');
            var tr = document.createElement('tr');
            tr.className = 'ingrediente-row';
            tr.innerHTML =
                '<td><div class="autocomplete-container">' +
                    '<input name="ingrediente_' + ingredienteCount + '" type="text" ' +
                    'id="ingrediente_' + ingredienteCount + '" ' +
                    'placeholder="Digite para buscar..." required style="width:100%;" autocomplete="off">' +
                    '<div id="autocomplete_' + ingredienteCount + '" class="autocomplete-list" style="display:none;"></div>' +
                '</div></td>' +
                '<td><input name="qtd_' + ingredienteCount + '" type="number" step="0.1" min="0.1" ' +
                'placeholder="Gramas" required style="width:100%;" autocomplete="off">' +
                '<div id="match_' + ingredienteCount + '" class="ingrediente-sugerido"></div></td>';
            container.appendChild(tr);
            setupAutocomplete(ingredienteCount);
            ingredienteCount++;
            document.getElementById('numIngredientes').value = ingredienteCount;
        }

        function setupAutocomplete(idx) {
            var input = document.getElementById('ingrediente_' + idx);
            var list = document.getElementById('autocomplete_' + idx);
            var matchInfo = document.getElementById('match_' + idx);
            var activeIdx = -1;
            if (!input) return;

            input.addEventListener('input', function() {
                var val = this.value.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '').trim();
                list.innerHTML = '';
                activeIdx = -1;
                if (val.length < 2) { list.style.display = 'none'; return; }

                var tokens = val.split(/\s+/);
                var matches = tacoData.filter(function(item) {
                    var desc = item.descricao.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '');
                    for (var t = 0; t < tokens.length; t++) {
                        if (desc.indexOf(tokens[t]) === -1) return false;
                    }
                    return true;
                });

                matches.sort(function(a, b) {
                    var da = a.descricao.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '');
                    var db = b.descricao.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '');
                    if (da === val) return -1;
                    if (db === val) return 1;
                    if (da.indexOf(val) === 0) return -1;
                    if (db.indexOf(val) === 0) return 1;
                    return a.descricao.localeCompare(b.descricao);
                });

                if (matches.length === 0) { list.style.display = 'none'; return; }

                var max = Math.min(matches.length, 10);
                for (var i = 0; i < max; i++) {
                    var div = document.createElement('div');
                    div.className = 'autocomplete-item';
                    (function(mItem) {
                        div.innerHTML = mItem.descricao + '<span class="ac-cat">[' + mItem.categoria + ']</span>';
                        div.addEventListener('click', function() {
                            input.value = mItem.descricao;
                            list.style.display = 'none';
                            if (matchInfo) {
                                matchInfo.innerHTML = '&#10004; TACO: <b>' + mItem.descricao + '</b> (' + mItem.energia + ' kcal/100g)';
                                matchInfo.style.color = 'green';
                            }
                        });
                    })(matches[i]);
                    div.addEventListener('mouseenter', function() {
                        list.querySelectorAll('.autocomplete-item').forEach(function(el) { el.classList.remove('active'); });
                        this.classList.add('active');
                        activeIdx = i;
                    });
                    list.appendChild(div);
                }
                list.style.display = 'block';
            });

            input.addEventListener('keydown', function(e) {
                var items = list.querySelectorAll('.autocomplete-item');
                if (!items.length) return;
                if (e.key === 'ArrowDown') { e.preventDefault(); activeIdx = Math.min(activeIdx + 1, items.length - 1); items.forEach(function(el) { el.classList.remove('active'); }); items[activeIdx].classList.add('active'); }
                else if (e.key === 'ArrowUp') { e.preventDefault(); activeIdx = Math.max(activeIdx - 1, 0); items.forEach(function(el) { el.classList.remove('active'); }); items[activeIdx].classList.add('active'); }
                else if (e.key === 'Enter' && activeIdx >= 0) { e.preventDefault(); items[activeIdx].click(); }
                else if (e.key === 'Escape') { list.style.display = 'none'; }
            });

            document.addEventListener('click', function(e) {
                if (!list.contains(e.target) && e.target !== input) { list.style.display = 'none'; }
            });
        }

        setupAutocomplete(0);
    </script>
</body>
</html>
