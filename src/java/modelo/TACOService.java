package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço que lê e fornece acesso aos dados da Tabela TACO.
 * Tenta múltiplos caminhos: classpath, filesystem relativo, caminho absoluto.
 */
public class TACOService {
    private List<ItemTACO> itens = new ArrayList<>();

    public TACOService() {
        carregarDados();
    }

    private void carregarDados() {
        // Tenta múltiplos caminhos
        String[] caminhos = {
            "/taco_data.json", 
            "/js/taco_data.json",  // classpath
            "web/js/taco_data.json",
            "./web/js/taco_data.json",
            "/home/guest/TabelaNutricional/build/web/js/taco_data.json",
            "/home/guest/TabelaNutricional/web/js/taco_data.json"
        };

        for (String caminho : caminhos) {
            if (itens.size() > 0) break;
            try {
                InputStream is;
                if (caminho.startsWith("/")) {
                    // Tentar como recurso do classpath
                    is = getClass().getResourceAsStream(caminho);
                    if (is == null) continue;
                } else {
                    // Tentar como arquivo do filesystem
                    File f = new File(caminho);
                    if (!f.exists()) continue;
                    is = new FileInputStream(f);
                }
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String linha;
                while ((linha = br.readLine()) != null) {
                    sb.append(linha);
                }
                itens = parsearJSON(sb.toString());
                br.close();
                System.out.println("TACO carregado com sucesso de: " + caminho + " (" + itens.size() + " itens)");
            } catch (Exception e) {
                System.err.println("Erro ao carregar TACO de " + caminho + ": " + e.getMessage());
            }
        }

        if (itens.isEmpty()) {
            System.err.println("AVISO: Nao foi possivel carregar a TACO de nenhum caminho. Usando lista vazia.");
        }
    }

    /**
     * Parser JSON manual (sem dependência externa) para ItemTACO.
     * Usa um parser simplista baseado em tokens.
     */
    private List<ItemTACO> parsearJSON(String json) {
        List<ItemTACO> resultado = new ArrayList<>();

        // Remove array externo
        json = json.trim();
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]")) json = json.substring(0, json.length() - 1);

        // Divide em objetos
        int depth = 0;
        int inicio = 0;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (depth == 0) inicio = i;
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0) {
                    String objStr = json.substring(inicio, i + 1);
                    resultado.add(parsearItem(objStr));
                }
            }
        }
        return resultado;
    }

    private ItemTACO parsearItem(String objStr) {
        ItemTACO item = new ItemTACO();
        // Remove chaves
        objStr = objStr.trim();
        if (objStr.startsWith("{")) objStr = objStr.substring(1);
        if (objStr.endsWith("}")) objStr = objStr.substring(0, objStr.length() - 1);

        // Divide por vírgulas respeitando strings
        List<String> pares = splitChaves(objStr);
        for (String par : pares) {
            int colonIdx = par.indexOf(':');
            if (colonIdx == -1) continue;
            String chave = limparString(par.substring(0, colonIdx));
            String valor = par.substring(colonIdx + 1).trim();

            double num = 0;
            try {
                if (valor.equals("null")) {
                    num = 0;
                } else {
                    num = Double.parseDouble(valor);
                }
            } catch (NumberFormatException e) {
                num = 0;
            }

            switch (chave) {
                case "codigo": item.setCodigo((int) num); break;
                case "descricao": item.setDescricao(limparString(valor)); break;
                case "categoria": item.setCategoria(limparString(valor)); break;
                case "umidade": item.setUmidade(num); break;
                case "energia": item.setEnergia(num); break;
                case "proteina": item.setProteina(num); break;
                case "lipideos": item.setLipideos(num); break;
                case "colesterol": item.setColesterol(num); break;
                case "carboidrato": item.setCarboidrato(num); break;
                case "fibra": item.setFibra(num); break;
                case "cinzas": item.setCinzas(num); break;
                case "calcio": item.setCalcio(num); break;
                case "magnesio": item.setMagnesio(num); break;
                case "manganes": item.setManganes(num); break;
                case "fosforo": item.setFosforo(num); break;
                case "ferro": item.setFerro(num); break;
                case "sodio": item.setSodio(num); break;
                case "potassio": item.setPotassio(num); break;
                case "cobre": item.setCobre(num); break;
                case "zinco": item.setZinco(num); break;
                case "retinol": item.setRetinol(num); break;
                case "vitamB1": item.setVitamB1(num); break;
                case "vitamB2": item.setVitamB2(num); break;
                case "vitamB6": item.setVitamB6(num); break;
                case "vitamB12": item.setVitamB12(num); break;
                case "vitamC": item.setVitamC(num); break;
                case "vitamD": item.setVitamD(num); break;
                case "vitamE": item.setVitamE(num); break;
                case "acidoGraxoSaturado": item.setAcidoGraxoSaturado(num); break;
                case "acidoGraxoMonoinsaturado": item.setAcidoGraxoMonoinsaturado(num); break;
                case "acidoGraxoPoliinsaturado": item.setAcidoGraxoPoliinsaturado(num); break;
            }
        }
        return item;
    }

    private List<String> splitChaves(String s) {
        List<String> partes = new ArrayList<>();
        int depth = 0;
        boolean inString = false;
        boolean escaped = false;
        int inicio = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (escaped) { escaped = false; continue; }
            if (c == '\\') { escaped = true; continue; }
            if (c == '"') { inString = !inString; continue; }
            if (inString) continue;
            if (c == '{') depth++;
            else if (c == '}') depth--;
            else if (c == ',' && depth == 0) {
                partes.add(s.substring(inicio, i).trim());
                inicio = i + 1;
            }
        }
        partes.add(s.substring(inicio).trim());
        return partes;
    }

    private String limparString(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * Retorna todos os itens da TACO.
     */
    public List<ItemTACO> buscarTodos() {
        return itens;
    }

    /**
     * Busca por nome aproximado (case-insensitive, sem acentos).
     * Retorna o primeiro item com maior similaridade.
     */
    public ItemTACO buscarPorNome(String nomeBusca) {
        if (nomeBusca == null || nomeBusca.trim().isEmpty()) return null;

        String normalizado = normalizar(nomeBusca);
        double melhorPontuacao = 0;
        ItemTACO melhor = null;

        for (ItemTACO item : itens) {
            String itemNorm = normalizar(item.getDescricao());
            double pontuacao = similaridade(normalizado, itemNorm);
            if (pontuacao > melhorPontuacao) {
                melhorPontuacao = pontuacao;
                melhor = item;
            }
        }

        // Retorna apenas se a similaridade for boa o suficiente (> 35%)
        return melhorPontuacao > 0.35 ? melhor : null;
    }

    private String normalizar(String texto) {
        // Converte para minúsculas, remove acentos e caracteres especiais
        String n = texto.toLowerCase()
                .replace("á", "a").replace("ã", "a").replace("ä", "a").replace("à", "a").replace("â", "a")
                .replace("é", "e").replace("ê", "e").replace("è", "e")
                .replace("í", "i")
                .replace("ó", "o").replace("õ", "o").replace("ö", "o").replace("ò", "o").replace("ô", "o")
                .replace("ú", "u").replace("ü", "u")
                .replace("ç", "c")
                .replace("ñ", "n")
                .replace("-", " ")
                .replace(",", " ")
                .replace(".", " ")
                .trim();
        // Remove espaços extras
        return n.replaceAll("\\s+", " ");
    }

    /**
     * Calcula similaridade combinada entre busca e candidato.
     * Combina: cobertura de tokens de busca, Jaccard, substring.
     * Retorna valor entre 0.0 e 1.0.
     */
    private double similaridade(String busca, String candidato) {
        String[] tokensBusca = busca.split("\\s+");
        String[] tokensCand = candidato.split("\\s+");

        if (tokensBusca.length == 0 || tokensCand.length == 0) return 0;

        java.util.Set<String> setBusca = new java.util.HashSet<>();
        java.util.Set<String> setCand = new java.util.HashSet<>();
        for (String t : tokensBusca) setBusca.add(t);
        for (String t : tokensCand) setCand.add(t);

        // 1) Cobertura: quantos tokens da busca estão no candidato?
        int cobertos = 0;
        for (String tb : tokensBusca) {
            if (setCand.contains(tb)) cobertos++;
        }
        double cobertura = (double) cobertos / tokensBusca.length;

        // 2) Jaccard
        java.util.Set<String> uniao = new java.util.HashSet<>(setBusca);
        uniao.addAll(setCand);
        java.util.Set<String> intersecao = new java.util.HashSet<>(setBusca);
        intersecao.retainAll(setCand);
        double jaccard = uniao.isEmpty() ? 0 : (double) intersecao.size() / uniao.size();

        // 3) Substring: a busca aparece como substring ou vice-versa
        String buscaSemEspaco = busca.replace(" ", "");
        String candSemEspaco = candidato.replace(" ", "");
        double substringBonus = 0;
        if (candSemEspaco.contains(buscaSemEspaco) && !buscaSemEspaco.isEmpty()) {
            substringBonus = 0.3;
        } else if (buscaSemEspaco.contains(candSemEspaco) && !candSemEspaco.isEmpty()) {
            substringBonus = 0.2;
        } else if (candidato.contains(busca) && !busca.isEmpty()) {
            substringBonus = 0.25;
        }

        // Score combinado: cobertura pesa mais
        double score = cobertura * 0.6 + jaccard * 0.1 + substringBonus;
        return Math.min(score, 1.0);
    }

    /**
     * Retorna N sugestoes ordenadas por score de similaridade.
     * Usado quando a busca principal nao encontrou correspondencia.
     */
    public List<ItemTACO> buscarSugestoes(String nomeBusca, int maximo) {
        if (nomeBusca == null || nomeBusca.trim().isEmpty()) return new ArrayList<>();

        String normalizado = normalizar(nomeBusca);

        // Lista com score (par item, score)
        java.util.ArrayList<double[]> scored = new java.util.ArrayList<>();
        for (ItemTACO item : itens) {
            String itemNorm = normalizar(item.getDescricao());
            double pontuacao = similaridade(normalizado, itemNorm);
            if (pontuacao > 0.15) {  // threshold menor para sugestoes
                scored.add(new double[]{item.getCodigo(), pontuacao});
            }
        }

        // Ordena por score decrescente
        scored.sort((a, b) -> Double.compare(b[1], a[1]));

        List<ItemTACO> resultado = new ArrayList<>();
        for (int i = 0; i < Math.min(scored.size(), maximo); i++) {
            int codigo = (int) scored.get(i)[0];
            for (ItemTACO item : itens) {
                if (item.getCodigo() == codigo) {
                    resultado.add(item);
                    break;
                }
            }
        }
        return resultado;
    }

    /**
     * Buscar com threshold customizavel (para sugestoes).
     */
    public ItemTACO buscarPorNome(String nomeBusca, boolean modoSugestao) {
        if (nomeBusca == null || nomeBusca.trim().isEmpty()) return null;

        String normalizado = normalizar(nomeBusca);
        double melhorPontuacao = 0;
        ItemTACO melhor = null;

        for (ItemTACO item : itens) {
            String itemNorm = normalizar(item.getDescricao());
            double pontuacao = similaridade(normalizado, itemNorm);
            if (pontuacao > melhorPontuacao) {
                melhorPontuacao = pontuacao;
                melhor = item;
            }
        }

        double threshold = modoSugestao ? 0.20 : 0.35;
        return melhorPontuacao > threshold ? melhor : null;
    }

    /**
     * Para testes standalone (requer caminho correto para o JSON).
     */
    public static void main(String[] args) {
        TACOService svc = new TACOService();
        System.out.println("Total de itens TACO: " + svc.buscarTodos().size());

        // Teste de busca
        String[] buscas = {"Pimenta dedo de moça", "sal", "arroz", "óleo de soja", "tomate", "feijão", "alho"};
        for (String termo : buscas) {
            ItemTACO item = svc.buscarPorNome(termo);
            if (item != null) {
                System.out.println("Busca '" + termo + "' → " + item.getDescricao() + " (energia: " + item.getEnergia() + " kcal)");
            } else {
                System.out.println("Busca '" + termo + "' → Não encontrado");
            }
        }
    }
}
