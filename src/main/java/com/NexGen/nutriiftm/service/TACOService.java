package com.NexGen.nutriiftm.service;

import com.NexGen.nutriiftm.model.ItemTACO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Adições ao TACOService original:
 *  - Mapa por código (HashMap) para lookup O(1) via buscarPorCodigo().
 *    Elimina a necessidade de repetir busca fuzzy em ReceitaService.calcularPreview().
 *  - Constante 4.184 removida — já está em NutricionalConstants.KCAL_TO_KJ.
 *    (O parser interno ainda usa o literal uma vez durante o load, aceitável.)
 */
@Service
public class TACOService {

    private List<ItemTACO> itens = new ArrayList<>();
    /** Lookup O(1) por código TBCA (String.valueOf(id)) */
    private Map<String, ItemTACO> itensPorCodigo = new HashMap<>();

    public TACOService() {
        carregarDados();
    }

    /** Retorna o item pelo código exato da TBCA. O(1). */
    public ItemTACO buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        return itensPorCodigo.get(codigo);
    }

    private void carregarDados() {
        String[] caminhos = { "/taco/TACO.json" };

        for (String caminho : caminhos) {
            if (!itens.isEmpty()) break;
            try {
                InputStream is;
                if (caminho.startsWith("/")) {
                    is = getClass().getResourceAsStream(caminho);
                    if (is == null) continue;
                } else {
                    File f = new File(caminho);
                    if (!f.exists()) continue;
                    is = new FileInputStream(f);
                }
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    StringBuilder sb = new StringBuilder();
                    String linha;
                    while ((linha = br.readLine()) != null) sb.append(linha);
                    itens = parsearJSON(sb.toString());
                }
                // Construir mapa de lookup
                for (ItemTACO item : itens) {
                    if (item.getCodigo() != null) {
                        itensPorCodigo.put(item.getCodigo(), item);
                    }
                }
                System.out.println("TBCA carregado: " + itens.size() + " itens de " + caminho);
            } catch (Exception e) {
                System.err.println("Erro ao carregar TBCA de " + caminho + ": " + e.getMessage());
            }
        }
        if (itens.isEmpty()) {
            System.err.println("AVISO: TBCA não carregada. Lista vazia.");
        }
    }

    // ── Restante do código original preservado integralmente ─────────────────

    private List<ItemTACO> parsearJSON(String json) {
        List<ItemTACO> resultado = new ArrayList<>();
        json = json.trim();
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]"))   json = json.substring(0, json.length() - 1);
        int depth = 0, inicio = 0;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') { if (depth == 0) inicio = i; depth++; }
            else if (c == '}') { depth--; if (depth == 0) resultado.add(parsearItem(json.substring(inicio, i + 1))); }
        }
        return resultado;
    }

    private double numVal(String valor) {
        if (valor == null) return 0.0;
        String v = valor.trim();
        if (v.isEmpty() || v.equalsIgnoreCase("NA") || v.equalsIgnoreCase("Tr")) return 0.0;
        if (v.startsWith("\"")) v = v.substring(1);
        if (v.endsWith("\""))   v = v.substring(0, v.length() - 1);
        try { return Double.parseDouble(v); } catch (NumberFormatException e) { return 0.0; }
    }

    private ItemTACO parsearItem(String objStr) {
        ItemTACO item = new ItemTACO();
        objStr = objStr.trim();
        if (objStr.startsWith("{")) objStr = objStr.substring(1);
        if (objStr.endsWith("}"))   objStr = objStr.substring(0, objStr.length() - 1);
        List<String> pares = splitChaves(objStr);
        for (String par : pares) {
            int colonIdx = par.indexOf(':');
            if (colonIdx == -1) continue;
            String chave  = limparString(par.substring(0, colonIdx));
            String valor  = par.substring(colonIdx + 1).trim();
            String strVal = limparString(valor);
            double num    = numVal(valor);
            switch (chave) {
                case "id"             -> { item.setId((int)num); item.setCodigo(String.valueOf((int)num)); }
                case "description"    -> item.setDescricao(strVal);
                case "category"       -> item.setCategoria(strVal);
                case "humidity_percents" -> item.setUmidade(num);
                case "energy_kcal"    -> { item.setEnergia(num); item.setEnergiaKj(Math.round(num * 4.184 * 10.0) / 10.0); }
                case "protein_g"      -> item.setProteina(num);
                case "lipid_g"        -> item.setLipideos(num);
                case "cholesterol_mg" -> item.setColesterol(num);
                case "carbohydrate_g" -> item.setCarboidrato(num);
                case "fiber_g"        -> item.setFibra(num);
                case "calcium_mg"     -> item.setCalcio(num);
                case "sodium_mg"      -> item.setSodio(num);
                case "magnesium_mg"   -> item.setMagnesio(num);
                case "manganese_mg"   -> item.setManganes(num);
                case "phosphorus_mg"  -> item.setFosforo(num);
                case "iron_mg"        -> item.setFerro(num);
                case "potassium_mg"   -> item.setPotassio(num);
                case "copper_mg"      -> item.setCobre(num);
                case "zinc_mg"        -> item.setZinco(num);
                case "retinol_mcg"    -> item.setRetinol(num);
                case "thiamine_mg"    -> item.setVitamB1(num);
                case "riboflavin_mg"  -> item.setVitamB2(num);
                case "pyridoxine_mg"  -> item.setVitamB6(num);
                case "cobalamin_mcg"  -> item.setVitamB12(num);
                case "vitaminC_mg"    -> item.setVitamC(num);
                case "vitaminD_mcg"   -> item.setVitamD(num);
                case "vitaminE_mg"    -> item.setVitamE(num);
                case "saturated_g"    -> item.setAcidoGraxoSaturado(num);
                case "monounsaturated_g" -> item.setAcidoGraxoMonoinsaturado(num);
                case "polyunsaturated_g" -> item.setAcidoGraxoPoliinsaturado(num);
                case "ash_g"          -> item.setCinzas(num);
            }
        }
        return item;
    }

    private List<String> splitChaves(String s) {
        List<String> partes = new ArrayList<>();
        int depth = 0; boolean inString = false, escaped = false; int inicio = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (escaped) { escaped = false; continue; }
            if (c == '\\') { escaped = true; continue; }
            if (c == '"') { inString = !inString; continue; }
            if (inString) continue;
            if (c == '{') depth++;
            else if (c == '}') depth--;
            else if (c == ',' && depth == 0) { partes.add(s.substring(inicio, i).trim()); inicio = i + 1; }
        }
        partes.add(s.substring(inicio).trim());
        return partes;
    }

    private String limparString(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) s = s.substring(1, s.length() - 1);
        return s;
    }

    public List<ItemTACO> buscarTodos() { return itens; }

    public ItemTACO buscarPorNome(String nomeBusca) {
        if (nomeBusca == null || nomeBusca.isBlank()) return null;
        String normalizado = normalizar(nomeBusca);
        double melhorPontuacao = 0; ItemTACO melhor = null;
        for (ItemTACO item : itens) {
            double p = similaridade(normalizado, normalizar(item.getDescricao()));
            if (p > melhorPontuacao) { melhorPontuacao = p; melhor = item; }
        }
        return melhorPontuacao > 0.35 ? melhor : null;
    }

    public List<ItemTACO> buscarSugestoes(String nomeBusca, int maximo) {
        if (nomeBusca == null || nomeBusca.isBlank()) return new ArrayList<>();
        String normalizado = normalizar(nomeBusca);
        List<double[]> scored = new ArrayList<>();
        for (int i = 0; i < itens.size(); i++) {
            double p = similaridade(normalizado, normalizar(itens.get(i).getDescricao()));
            if (p > 0.15) scored.add(new double[]{i, p});
        }
        scored.sort((a, b) -> Double.compare(b[1], a[1]));
        List<ItemTACO> resultado = new ArrayList<>();
        for (int i = 0; i < Math.min(scored.size(), maximo); i++) resultado.add(itens.get((int)scored.get(i)[0]));
        return resultado;
    }

    private String normalizar(String texto) {
        return texto.toLowerCase()
            .replace("á","a").replace("ã","a").replace("ä","a").replace("à","a").replace("â","a")
            .replace("é","e").replace("ê","e").replace("è","e")
            .replace("í","i").replace("ó","o").replace("õ","o").replace("ö","o")
            .replace("ò","o").replace("ô","o").replace("ú","u").replace("ü","u")
            .replace("ç","c").replace("ñ","n").replace("-"," ").replace(","," ").replace("."," ")
            .trim().replaceAll("\\s+"," ");
    }

    private double similaridade(String busca, String candidato) {
        String[] tokensBusca = busca.split("\\s+");
        String[] tokensCand  = candidato.split("\\s+");
        if (tokensBusca.length == 0 || tokensCand.length == 0) return 0;
        Set<String> setBusca = new HashSet<>(Arrays.asList(tokensBusca));
        Set<String> setCand  = new HashSet<>(Arrays.asList(tokensCand));
        int cobertos = 0;
        for (String tb : tokensBusca) if (setCand.contains(tb)) cobertos++;
        double cobertura = (double) cobertos / tokensBusca.length;
        Set<String> uniao = new HashSet<>(setBusca); uniao.addAll(setCand);
        Set<String> intersecao = new HashSet<>(setBusca); intersecao.retainAll(setCand);
        double jaccard = uniao.isEmpty() ? 0 : (double) intersecao.size() / uniao.size();
        String buscaSemEspaco = busca.replace(" ","");
        String candSemEspaco  = candidato.replace(" ","");
        double substringBonus = 0;
        if (!buscaSemEspaco.isEmpty() && candSemEspaco.contains(buscaSemEspaco)) substringBonus = 0.3;
        else if (!candSemEspaco.isEmpty() && buscaSemEspaco.contains(candSemEspaco)) substringBonus = 0.2;
        else if (!busca.isEmpty() && candidato.contains(busca)) substringBonus = 0.25;
        return Math.min(cobertura * 0.6 + jaccard * 0.1 + substringBonus, 1.0);
    }
}