<%@page import="modelo.TabelaNutricional"%>
<%@page import="controle.TabelaNutricionalControle"%>
<%@page import="modelo.UnidadeMedida"%>
<%@page import="controle.UnidadeMedidaControle"%>
<%@page import="modelo.Elemento"%>
<%@page import="controle.ElementoControle"%>
<%@page import="modelo.Fabricante"%>
<%@page import="modelo.TabNutElemento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controle.TabNutElementoControle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    String nomeProduto = request.getParameter("produto");
    String pPorcao = request.getParameter("porcao");
    if (nomeProduto == null || nomeProduto.trim().isEmpty() || pPorcao == null || pPorcao.isEmpty()) {
        response.sendRedirect("inserirReceita.jsp?erro=falta_dados&produto=" + (nomeProduto != null ? nomeProduto : ""));
        return;
    }
    double porcao = 0;
    try {
        porcao = Double.parseDouble(pPorcao.replace(",", "."));
    } catch (NumberFormatException e) {
        System.out.println("Erro ao converter porcao: " + pPorcao);
        e.printStackTrace();
    }
    String unidade = request.getParameter("unidade") != null ? request.getParameter("unidade") : "g";
    double pesoTotal = 0, energia = 0, carboidrato = 0, proteina = 0, lipideos = 0, saturado = 0, fibra = 0, sodio = 0;
    try {
        pesoTotal   = Double.parseDouble(request.getParameter("pesoTotal").replace(",", "."));
        energia     = Double.parseDouble(request.getParameter("energia").replace(",", "."));
        carboidrato = Double.parseDouble(request.getParameter("carboidrato").replace(",", "."));
        proteina    = Double.parseDouble(request.getParameter("proteina").replace(",", "."));
        lipideos    = Double.parseDouble(request.getParameter("lipideos").replace(",", "."));
        saturado    = Double.parseDouble(request.getParameter("saturado").replace(",", "."));
        fibra       = Double.parseDouble(request.getParameter("fibra").replace(",", "."));
        sodio       = Double.parseDouble(request.getParameter("sodio").replace(",", "."));
    } catch (Exception ex) {
        System.err.println("Erro ao converter valores nutricionais: " + ex.getMessage());
    }

    // Debug log
    System.out.println("[salvarReceita] produto=" + nomeProduto + ", pesoTotal=" + pesoTotal + ", energia=" + energia + ", carboidrato=" + carboidrato + ", proteina=" + proteina + ", lipideos=" + lipideos);

    // Campos adicionais do formulario
    String nomeFantasia = request.getParameter("nomeFantasia");
    if (nomeFantasia == null) nomeFantasia = "";
    String ingredientes = request.getParameter("ingredientes");
    if (ingredientes == null) ingredientes = "";
    String fabricacao = request.getParameter("dataFabricacao");
    if (fabricacao == null || fabricacao.isEmpty()) fabricacao = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    String vencimento = request.getParameter("dataVencimento");
    if (vencimento == null || vencimento.isEmpty()) vencimento = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    String recomendacoes = request.getParameter("recomendacoes");
    if (recomendacoes == null || recomendacoes.isEmpty()) recomendacoes = "Manter em local seco e arejado. Após aberto armazenar sob refrigeração.";

    // Fabricante selecionado ou busca pelo nome "Teste"
    int fabCodigoPadrao = 0;
    String fabParam = request.getParameter("fabricante");
    if (fabParam != null && !fabParam.isEmpty()) {
        try { fabCodigoPadrao = Integer.parseInt(fabParam); }
        catch (NumberFormatException e) { fabCodigoPadrao = 0; }
    }
    // Se nenhum fabricante foi selecionado, busca o primeiro ou usa teste
    if (fabCodigoPadrao == 0) {
        controle.FabricanteControle fabCtrl = new controle.FabricanteControle();
        ArrayList<Fabricante> fabs = fabCtrl.consultarFabricantes();
        for (modelo.Fabricante f : fabs) {
            if (f.getFabNome().contains("Teste")) {
                fabCodigoPadrao = f.getFabCodigo();
                break;
            }
        }
        if (fabCodigoPadrao == 0 && !fabs.isEmpty()) {
            fabCodigoPadrao = fabs.get(0).getFabCodigo();
        }
    }

    // 1. Inserir o produto
    controle.ProdutoControle prodCtrl = new controle.ProdutoControle();
    modelo.Produto produto = new modelo.Produto();
    produto.setProNome(nomeProduto);
    produto.setProPeso(pesoTotal > 0 ? pesoTotal : porcao); // Fallback: usa porcao se pesoTotal for 0
    produto.setFabCodigo(fabCodigoPadrao > 0 ? fabCodigoPadrao : 1);
    produto.setProDataFabricacao(fabricacao);
    produto.setProDataVencimento(vencimento);
    produto.setProRecomendacoes(recomendacoes);
    produto.setProIngredientes(ingredientes);
    produto.setProNomeFantasia(nomeFantasia);

    String resProd = prodCtrl.inserirProduto(produto);

    // Buscar o produto inserido pelo código (mais confiável que por nome)
    modelo.Produto prodInserido = null;
    if ("inserido".equals(resProd)) {
        ArrayList<modelo.Produto> todos = prodCtrl.consultarProdutos();
        // Busca reverso: encontra o último produto com esse nome e fabricante
        for (int i = todos.size() - 1; i >= 0; i--) {
            modelo.Produto p = todos.get(i);
            if (p.getProNome().equals(nomeProduto) && p.getFabCodigo() == (fabCodigoPadrao > 0 ? fabCodigoPadrao : 1)) {
                prodInserido = p;
                break;
            }
        }
    }

    if (prodInserido != null) {
        // 2. Criar tabela nutricional
        TabelaNutricionalControle tnc = new TabelaNutricionalControle();
        TabelaNutricional tn = new TabelaNutricional();
        tn.setProCodigo(prodInserido.getProCodigo());

        UnidadeMedidaControle umc = new UnidadeMedidaControle();
        int undCodigo = 6; // g padrão
        for (UnidadeMedida um : umc.consultarUnidadeMedida()) {
            if (um.getUndNome().equals(unidade)) {
                undCodigo = um.getUndCodigo();
                break;
            }
        }
        tn.setUndCodigo(undCodigo);
        tn.setTabPorcao(porcao);
        tn.setTabValorEnergeticoPorcao(energia);
        tn.setTabTotalColheres(0);
        tn.setTabUnidadeMedidasColheres(undCodigo);

        String tnResult = tnc.inserirTabelaNutricional(tn);

        if ("inserido".equals(tnResult)) {
            int tabCodigo = tnc.consultarTabelaNutricional(tn);

            // 3. Inserir nutrientes
            ElementoControle ec = new ElementoControle();
            ArrayList<Elemento> elementos = ec.consultarElementos();
            TabNutElementoControle tnec = new TabNutElementoControle();

            for (Elemento ele : elementos) {
                TabNutElemento tne = new TabNutElemento();
                tne.setTabCodigo(tabCodigo);
                tne.setEleCodigo(ele.getEleCodigo());

                double valor = 0;
                String eleNome = ele.getEleNome().trim();
                switch (eleNome) {
                    case "Carboridratos (g)": valor = carboidrato; break;
                    case "Açúcares Totais (g)": valor = 0; break;
                    case "Açúcares Adicionados (g)": valor = 0; break;
                    case "Proteínas (g)": valor = proteina; break;
                    case "Gorduras Totais (g)": valor = lipideos; break;
                    case "Gorduras Saturadas (g)": valor = saturado; break;
                    case "Gorduras Trans (g)": valor = 0; break;
                    case "Fibras Alimentares (g)": valor = fibra; break;
                    case "Sódio (mg)": valor = sodio; break;
                }
                tne.setTneValor(valor);
                if (valor > 0) {
                    tnec.inserirElementosNutricionais(tne);
                }
            }

            out.println("<html><head><title>Sucesso - Nutri IFTM</title>");
            out.println("<link rel='stylesheet' href='css/bootstrap.min.css'><link rel='stylesheet' href='css/style.css'>");
            out.println("</head><body class='main-layout'>");
            out.println("<div class='container' style='margin-top:50px;'>");
            out.println("<div class='box_text'><h3>Receita salva com sucesso!</h3>");
            out.println("<p>Produto: " + nomeProduto + "</p>");
            out.println("<p>Peso total: " + pesoTotal + unidade + " | Porção: " + porcao + " " + unidade + "</p>");
            out.println("<p>Unidade: " + unidade + " | Fabricante: " + prodInserido.getFabCodigo() + "</p>");
            if (!ingredientes.isEmpty()) out.println("<p><b>Ingredientes:</b> " + ingredientes + "</p>");
            out.println("<p><b>Código da Tabela Nutricional: " + tabCodigo + "</b></p>");
            out.println("<br><a href='inserirReceita.jsp' class='btn btn-primary'>Nova Receita</a> ");
            out.println("<a href='tabela.jsp' class='btn btn-secondary'>Ver Tabelas</a>");
            out.println("<a href='index.html' class='btn btn-secondary'>Página Principal</a>");
            out.println("</div></div></body></html>");
        } else {
            out.println("<html><head><title>Erro - Nutri IFTM</title>");
            out.println("<link rel='stylesheet' href='css/bootstrap.min.css'><link rel='stylesheet' href='css/style.css'>");
            out.println("</head><body class='main-layout'>");
            out.println("<div class='container' style='margin-top:50px;'>");
            out.println("<div class='box_text'><h3>Erro ao salvar tabela nutricional.</h3>");
            out.println("<a href='inserirReceita.jsp' class='btn btn-primary'>Voltar</a></div></div></body></html>");
        }
    } else if ("inserido".equals(resProd)) {
        out.println("<html><head><title>Erro - Nutri IFTM</title>");
        out.println("<link rel='stylesheet' href='css/bootstrap.min.css'><link rel='stylesheet' href='css/style.css'>");
        out.println("</head><body class='main-layout'>");
        out.println("<div class='container' style='margin-top:50px;'>");
        out.println("<div class='box_text'><h3>Produto inserido mas não localizado na consulta de tabelas.</h3>");
        out.println("<a href='inserirReceita.jsp' class='btn btn-primary'>Voltar</a></div></div></body></html>");
    } else {
        out.println("<html><head><title>Erro - Nutri IFTM</title>");
        out.println("<link rel='stylesheet' href='css/bootstrap.min.css'><link rel='stylesheet' href='css/style.css'>");
        out.println("</head><body class='main-layout'>");
        out.println("<div class='container' style='margin-top:50px;'>");
        out.println("<div class='box_text'><h3>Erro ao salvar produto: " + resProd + "</h3>");
        out.println("<a href='inserirReceita.jsp' class='btn btn-primary'>Voltar</a></div></div></body></html>");
    }
%>
