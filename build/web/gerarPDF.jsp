<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="net.sf.jasperreports.engine.*"%>
<%@page import="net.sf.jasperreports.engine.design.*"%>
<%@page import="net.sf.jasperreports.engine.type.*"%>
<%@page import="net.sf.jasperreports.engine.data.JRMapCollectionDataSource"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="application/pdf; charset=utf-8" pageEncoding="UTF-8"%>
<%
    String param = request.getParameter("tabCodigo");
    if (param == null || param.isEmpty()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Codigo da tabela obrigatoria");
        return;
    }
    int tabCodigo = Integer.parseInt(param);
    conexao.ConexaoMySQL cmx = new conexao.ConexaoMySQL();
    Connection conn = cmx.conectar();
    try {
        String sql =
            "SELECT a.proCodigo, a.proNome, a.proPeso, " +
            "b.tabCodigo, b.tabPorcao, b.tabValorEnergeticoPorcao, " +
            "c.tneValor, c.eleCodigo, " +
            "d.undNome, e.fabNome, " +
            "f.eleNome, f.eleOrdem " +
            "FROM produto a " +
            "INNER JOIN tabelanutricional b ON a.proCodigo = b.proCodigo " +
            "LEFT JOIN tabnutelemento c ON b.tabCodigo = c.tabCodigo " +
            "INNER JOIN unidademedida d ON b.undCodigo = d.undCodigo " +
            "INNER JOIN fabricante e ON a.fabCodigo = e.fabCodigo " +
            "LEFT JOIN elemento f ON c.eleCodigo = f.eleCodigo " +
            "WHERE b.tabCodigo = ? " +
            "ORDER BY f.eleOrdem";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, tabCodigo);
        ResultSet rs = ps.executeQuery();

        String proNome = "";
        String undNome = "g";
        double tabPorcao = 0;
        double tabTotalPorcao = 0;
        double energia100g = 0;
        double energiaPorcao = 0;
        double vdEnergia = 0;
        List<Map<String, Object>> nutrientes = new ArrayList<>();

        Map<String, Double> VD = new HashMap<>();
        VD.put("Carboridratos (g)", 300.0);
        VD.put("Açúcares Totais (g)", null);
        VD.put("Açúcares Adicionados (g)", null);
        VD.put("Proteínas (g)", 50.0);
        VD.put("Gorduras Totais (g)", 65.0);
        VD.put("Gorduras Saturadas (g)", 20.0);
        VD.put("Gorduras Trans (g)", 2.0);
        VD.put("Gorduras monoinsaturadas (g)", 20.0);
        VD.put("Gorduras poli-insaturadas (g)", 20.0);
        VD.put("Ômega 6 (g)", 18.0);
        VD.put("Ômega 3 (mg)", 4000.0);
        VD.put("Colesterol (mg)", 300.0);
        VD.put("Fibras Alimentares (g)", 25.0);
        VD.put("Sódio (mg)", 2000.0);
        VD.put("Vitamina A (mg)", 800.0);
        VD.put("Vitamina D (mg)", 15.0);
        VD.put("Vitamina E (mg)", 15.0);
        VD.put("Vitamina K (mg)", 120.0);
        VD.put("Vitamína C (mg)", 100.0);
        VD.put("Tiamina (mg)", 1.2);
        VD.put("Riboflavina (mg)", 1.2);
        VD.put("Niacina (mg)", 15.0);
        VD.put("Vitamina B6 (mg)", 1.3);
        VD.put("Ácido fólico (mg)", 400.0);
        VD.put("Ácido pantotênico (mg)", 5.0);
        VD.put("Vitamina B12 (mg)", 2.4);
        VD.put("Cálcio (mg)", 1000.0);
        VD.put("Cloreto (mg)", 2300.0);
        VD.put("Cobre (mg)", 900.0);
        VD.put("Cromo (mg)", 35.0);
        VD.put("Ferro (mg)", 14.0);
        VD.put("Flúor (mg)", 4.0);
        VD.put("Fósforo (mg)", 700.0);
        VD.put("Iodo (mg)", 150.0);
        VD.put("Magnésio (mg)", 420.0);
        VD.put("Manganês (mg)", 3.0);
        VD.put("Molibdênio (mg)", 45.0);
        VD.put("Potássio (mg)", 3500.0);
        VD.put("Selênio (mg)", 60.0);
        VD.put("Zinco (mg)", 11.0);
        VD.put("Colina (mg)", 550.0);

        while (rs.next()) {
            proNome = rs.getString("proNome");
            undNome = rs.getString("undNome");
            tabPorcao = rs.getDouble("tabPorcao");
            double proPeso = rs.getDouble("proPeso");
            tabTotalPorcao = (tabPorcao > 0) ? Math.round(proPeso / tabPorcao) : 0;
            energiaPorcao = rs.getDouble("tabValorEnergeticoPorcao");
            energia100g = (tabPorcao > 0) ? Math.round(energiaPorcao * 100.0 / tabPorcao * 10.0) / 10.0 : 0;
            vdEnergia = (energiaPorcao / 2000.0) * 100.0;
            String eleNome = rs.getString("eleNome");
            double tneValor = rs.getDouble("tneValor");
            if (eleNome != null && !eleNome.trim().isEmpty()) {
                String nomeLimpo = eleNome.trim();
                double vd = 0;
                Double vdRef = VD.get(nomeLimpo);
                if (vdRef != null && vdRef > 0) {
                    vd = Math.round((tneValor / vdRef) * 100.0 * 10.0) / 10.0;
                }
                double valor100g = (tabPorcao > 0) ? Math.round(tneValor * 100.0 / tabPorcao * 10.0) / 10.0 : 0;
                Map<String, Object> nut = new HashMap<>();
                nut.put("nome", nomeLimpo);
                nut.put("valor100g", valor100g);
                nut.put("valorPorcao", tneValor);
                nut.put("vd", vd);
                nutrientes.add(nut);
            }
        }
        rs.close();
        ps.close();

        // --- Montar dados para o datasource ---
        List<Map<String, Object>> dados = new ArrayList<>();

        // Linha energética
        Map<String, Object> rowEn = new HashMap<>();
        rowEn.put("label", "Valor energetico (kcal)");
        rowEn.put("valor100g", String.format("%.1f", energia100g));
        rowEn.put("valorPorcao", String.format("%.1f", energiaPorcao));
        rowEn.put("vd", String.format("%.0f", vdEnergia));
        dados.add(rowEn);

        for (Map<String, Object> nut : nutrientes) {
            Map<String, Object> row = new HashMap<>();
            row.put("label", nut.get("nome").toString());
            row.put("valor100g", String.format("%.1f", (Double) nut.get("valor100g")));
            row.put("valorPorcao", String.format("%.1f", (Double) nut.get("valorPorcao")));
            double vdVal = (Double) nut.get("vd");
            row.put("vd", vdVal > 0 ? String.format("%.0f", vdVal) : "-");
            dados.add(row);
        }
        int titleHeight = 64;
        int columnHeaderHeight = 18;
        int detailHeight = 16;
        int footerHeight = 20;
        int margins = 8; // top + bottom

        int totalRows = dados.size(); // energia + nutrientes
        int pageHeight = titleHeight + columnHeaderHeight + (detailHeight * totalRows) + footerHeight + margins;
        // --- Construir relatório ---
        JasperDesign report = new JasperDesign();
        report.setName("TabelaNutricional");
        report.setPageWidth(300);
        report.setPageHeight(pageHeight);
        report.setColumnWidth(296);
        report.setLeftMargin(2);
        report.setRightMargin(2);
        report.setTopMargin(4);
        report.setBottomMargin(4);
        report.setWhenNoDataType(WhenNoDataTypeEnum.NO_PAGES);

        // Fields
        for (String fname : new String[]{"label","valor100g","valorPorcao","vd"}) {
            JRDesignField fld = new JRDesignField();
            fld.setName(fname);
            fld.setValueClass(String.class);
            report.addField(fld);
        }

        // =====================
        // TITLE BAND
        // =====================
        JRDesignBand title = new JRDesignBand();
        title.setHeight(60);

        // Retângulo externo
        JRDesignRectangle rect = new JRDesignRectangle();
        rect.setX(0); rect.setY(0); rect.setWidth(296); rect.setHeight(58);
        rect.getLinePen().setLineWidth(2.0f);
        title.addElement(rect);

        // Título principal
        JRDesignStaticText stTitulo = new JRDesignStaticText();
        stTitulo.setX(0); stTitulo.setY(2); stTitulo.setWidth(296); stTitulo.setHeight(20);
        stTitulo.setText("INFORMACAO NUTRICIONAL");
        stTitulo.setFontSize(14f);
        stTitulo.setFontName("SansSerif");
        stTitulo.setBold(true);
        stTitulo.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        title.addElement(stTitulo);

        // Linha separadora abaixo do título
        JRDesignLine linhaTitulo = new JRDesignLine();
        linhaTitulo.setX(0); linhaTitulo.setY(22); linhaTitulo.setWidth(296); linhaTitulo.setHeight(0);
        linhaTitulo.getLinePen().setLineWidth(2.0f);
        title.addElement(linhaTitulo);

        // Produto
        JRDesignStaticText stProdLabel = new JRDesignStaticText();
        stProdLabel.setX(4); stProdLabel.setY(26); stProdLabel.setWidth(55); stProdLabel.setHeight(14);
        stProdLabel.setText("Produto:");
        stProdLabel.setFontSize(8f); stProdLabel.setFontName("SansSerif"); stProdLabel.setBold(true);
        title.addElement(stProdLabel);

        JRDesignStaticText stProdVal = new JRDesignStaticText();
        stProdVal.setX(60); stProdVal.setY(26); stProdVal.setWidth(230); stProdVal.setHeight(14);
        stProdVal.setText(proNome);
        stProdVal.setFontSize(8f); stProdVal.setFontName("SansSerif"); stProdVal.setBold(false);
        title.addElement(stProdVal);

        // Porção
        JRDesignStaticText stPorcLabel = new JRDesignStaticText();
        stPorcLabel.setX(4); stPorcLabel.setY(42); stPorcLabel.setWidth(55); stPorcLabel.setHeight(14);
        stPorcLabel.setText("Porcao:");
        stPorcLabel.setFontSize(8f); stPorcLabel.setFontName("SansSerif"); stPorcLabel.setBold(true);
        title.addElement(stPorcLabel);

        String porcaoStr = String.format("%.0f %s (%.0f porcoes por embalagem)", tabPorcao, undNome, tabTotalPorcao);
        JRDesignStaticText stPorcVal = new JRDesignStaticText();
        stPorcVal.setX(60); stPorcVal.setY(42); stPorcVal.setWidth(230); stPorcVal.setHeight(14);
        stPorcVal.setText(porcaoStr);
        stPorcVal.setFontSize(8f); stPorcVal.setFontName("SansSerif"); stPorcVal.setBold(false);
        title.addElement(stPorcVal);

        report.setTitle(title);

        // =====================
        // COLUMN HEADER BAND
        // =====================
        JRDesignBand columnHeader = new JRDesignBand();
        columnHeader.setHeight(18);

        JRDesignLine chLineTop = new JRDesignLine();
        chLineTop.setX(0); chLineTop.setY(0); chLineTop.setWidth(296); chLineTop.setHeight(0);
        chLineTop.getLinePen().setLineWidth(2.0f);
        columnHeader.addElement(chLineTop);

        JRDesignLine chLineBot = new JRDesignLine();
        chLineBot.setX(0); chLineBot.setY(16); chLineBot.setWidth(296); chLineBot.setHeight(0);
        chLineBot.getLinePen().setLineWidth(1.5f);
        columnHeader.addElement(chLineBot);

        // Helper para header estático
        String[] hTexts = {"Nutriente", "Por 100"+undNome, "Por porcao", "%VD*"};
        int[] hX      = {4,   160, 210, 258};
        int[] hW      = {155,  48,  46,  34};
        boolean[] hCenter = {false, true, true, true};
        for (int i = 0; i < hTexts.length; i++) {
            JRDesignStaticText sh = new JRDesignStaticText();
            sh.setX(hX[i]); sh.setY(1); sh.setWidth(hW[i]); sh.setHeight(14);
            sh.setText(hTexts[i]);
            sh.setFontSize(8f); sh.setFontName("SansSerif"); sh.setBold(true);
            if (hCenter[i]) sh.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
            columnHeader.addElement(sh);
        }
        report.setColumnHeader(columnHeader);

        // =====================
        // DETAIL BAND
        // =====================
        JRDesignBand detail = new JRDesignBand();
        detail.setHeight(16);

        JRDesignLine detLine = new JRDesignLine();
        detLine.setX(0); detLine.setY(0); detLine.setWidth(296); detLine.setHeight(0);
        detLine.getLinePen().setLineWidth(0.5f);
        detail.addElement(detLine);

        // label
        JRDesignTextField dtLabel = new JRDesignTextField();
        dtLabel.setX(4); dtLabel.setY(2); dtLabel.setWidth(155); dtLabel.setHeight(13);
        dtLabel.setFontSize(8f); dtLabel.setFontName("SansSerif"); dtLabel.setBold(false);
        JRDesignExpression exLabel = new JRDesignExpression();
        exLabel.setText("$F{label}"); exLabel.setValueClass(String.class);
        dtLabel.setExpression(exLabel);
        detail.addElement(dtLabel);

        // valor100g
        JRDesignTextField dt100 = new JRDesignTextField();
        dt100.setX(160); dt100.setY(2); dt100.setWidth(48); dt100.setHeight(13);
        dt100.setFontSize(8f); dt100.setFontName("SansSerif");
        dt100.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        JRDesignExpression ex100 = new JRDesignExpression();
        ex100.setText("$F{valor100g}"); ex100.setValueClass(String.class);
        dt100.setExpression(ex100);
        detail.addElement(dt100);

        // valorPorcao
        JRDesignTextField dtPor = new JRDesignTextField();
        dtPor.setX(210); dtPor.setY(2); dtPor.setWidth(46); dtPor.setHeight(13);
        dtPor.setFontSize(8f); dtPor.setFontName("SansSerif");
        dtPor.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        JRDesignExpression exPor = new JRDesignExpression();
        exPor.setText("$F{valorPorcao}"); exPor.setValueClass(String.class);
        dtPor.setExpression(exPor);
        detail.addElement(dtPor);

        // vd
        JRDesignTextField dtVD = new JRDesignTextField();
        dtVD.setX(258); dtVD.setY(2); dtVD.setWidth(34); dtVD.setHeight(13);
        dtVD.setFontSize(8f); dtVD.setFontName("SansSerif");
        dtVD.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        JRDesignExpression exVD = new JRDesignExpression();
        exVD.setText("$F{vd}"); exVD.setValueClass(String.class);
        dtVD.setExpression(exVD);
        detail.addElement(dtVD);

        JRDesignSection detailSection = (JRDesignSection) report.getDetailSection();
        detailSection.addBand(detail);

        // =====================
        // PAGE FOOTER
        // =====================
        JRDesignBand pageFooter = new JRDesignBand();
        pageFooter.setHeight(16);

        JRDesignLine footLine = new JRDesignLine();
        footLine.setX(0); footLine.setY(0); footLine.setWidth(296); footLine.setHeight(0);
        footLine.getLinePen().setLineWidth(1.5f);
        pageFooter.addElement(footLine);

        JRDesignStaticText stNota = new JRDesignStaticText();
        stNota.setX(0); stNota.setY(2); stNota.setWidth(296); stNota.setHeight(12);
        stNota.setText("* Percentuais de Valores Diarios com base em dieta de 2.000 kcal.");
        stNota.setFontSize(6f); stNota.setFontName("SansSerif"); stNota.setBold(false);
        stNota.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        pageFooter.addElement(stNota);

        report.setPageFooter(pageFooter);

        // --- Compilar e gerar PDF ---
        JasperReport jasperReport = JasperCompileManager.compileReport(report);
        Collection<Map<String,?>> dadosTyped = new ArrayList<Map<String,?>>(dados);
        JRDataSource dataSource = new JRMapCollectionDataSource(dadosTyped);
        byte[] pdf = JasperRunManager.runReportToPdf(jasperReport, new HashMap<>(), dataSource);

        response.setContentType("application/pdf");
        response.setContentLength(pdf.length);
        response.setHeader("Content-Disposition",
                "inline; filename=tabela_nutricional_" + tabCodigo + ".pdf");
        ServletOutputStream outStream = response.getOutputStream();
        outStream.write(pdf);
        outStream.flush();
        outStream.close();

    } catch (Exception e) {
        System.err.println("Erro ao gerar PDF: " + e.getMessage());
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Erro ao gerar PDF: " + e.getMessage());
    } finally {
        if (conn != null) {
            try { if (!conn.isClosed()) conn.close(); } catch (SQLException ex) {}
        }
    }
%>