package com.NexGen.nutriiftm.controller;

import com.NexGen.nutriiftm.model.*;
import com.NexGen.nutriiftm.service.*;
import com.NexGen.nutriiftm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/calculadora")
@RequiredArgsConstructor
public class CalculadoraController {

    private final TACOService tacoService;
    private final MacronutrientesService macroService;
    private final TabelaNutricionalService tabelaService;
    private final ProdutoService produtoService;
    private final UnidadeMedidaService unidadeService;
    private final ElementoRepository elementoRepo;
    private final TabNutElementoRepository tneRepo;

    @GetMapping
    public String form(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        model.addAttribute("unidades", unidadeService.listarTodos());
        return "calculadora";
    }

    // Endpoint AJAX: retorna todos os itens TACO como JSON para autocomplete
    @GetMapping("/taco-dados")
    @ResponseBody
    public List<Map<String, Object>> tacoDados() {
        List<Map<String, Object>> lista = new ArrayList<>();
        for (ItemTACO item : tacoService.buscarTodos()) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("codigo", item.getCodigo());
            m.put("descricao", item.getDescricao());
            m.put("categoria", item.getCategoria());
            m.put("energia", item.getEnergia());
            lista.add(m);
        }
        return lista;
    }

    // Calcula preview sem salvar — chamado via AJAX com JSON
    // No CalculadoraController, troque a assinatura de calcular():
@PostMapping("/calcular")
@ResponseBody
public Map<String, Object> calcular(
        @RequestBody List<Map<String, Object>> ingredientes,
        @RequestParam(defaultValue = "100") double porcao
) {
    Receita receita = montarReceita(ingredientes, porcao);
    ValoresNutricionais v = macroService.calcular(receita);
    return nutrientesParaMap(v, receita.getPesoTotalG());
}

    // Salva como TabelaNutricional + TabNutElemento
    @PostMapping("/salvar")
    public String salvar(
            @RequestParam Long produtoId,
            @RequestParam Long unidadeId,
            @RequestParam double porcao,
            @RequestParam double totalPorcoes,
            @RequestParam(required = false, defaultValue = "0") double totalColheres,
            @RequestParam String ingredientesJson
    ) {
        // Parseia ingredientes do JSON enviado pelo form
        List<Map<String, Object>> ingredientes = parsearIngredientesJson(ingredientesJson);

        Receita receita = montarReceita(ingredientes, porcao);
        ValoresNutricionais v = macroService.calcular(receita);

        // Monta TabelaNutricional
        TabelaNutricional tabela = new TabelaNutricional();
        tabela.setProduto(produtoService.buscarPorId(produtoId));
        tabela.setUnidadeMedida(unidadeService.buscarPorId(unidadeId));
        tabela.setTabPorcao(porcao);
        tabela.setTabTotalPorcao(totalPorcoes);
        tabela.setTabTotalColheres(totalColheres);
        tabela.setTabValorEnergeticoPorcao(v.getEnergiaPorcao());
        tabela.setTabValorEnergetico(v.getEnergia100g());
        tabela.setTabVD(v.getVDEnergia());
        tabela.setTabPorcaoPadrao(porcao);

        TabelaNutricional salva = tabelaService.salvar(tabela);

        // Mapeia nutrientes calculados → Elemento do banco
        Map<String, Double> nutrientes = new LinkedHashMap<>();
        nutrientes.put("Carboidratos (g)",        v.getCarboidratoPorcao());
        nutrientes.put("Proteínas (g)",            v.getProteinaPorcao());
        nutrientes.put("Gorduras Totais (g)",      v.getLipideosPorcao());
        nutrientes.put("Gorduras Saturadas (g)",   v.getSaturadoPorcao());
        nutrientes.put("Gorduras Trans (g)",       0.0);
        nutrientes.put("Fibras Alimentares (g)",   v.getFibraPorcao());
        nutrientes.put("Sódio (mg)",               v.getSodioPorcao());

        List<Elemento> elementos = elementoRepo.findAll();
        List<TabNutElemento> tneList = new ArrayList<>();

        for (Map.Entry<String, Double> entry : nutrientes.entrySet()) {
            double valor = entry.getValue();
            // Só salva se > 0 (exceto os obrigatórios pela ANVISA)
            boolean obrigatorio = Set.of(
                "Carboidratos (g)", "Proteínas (g)", "Gorduras Totais (g)",
                "Gorduras Saturadas (g)", "Gorduras Trans (g)",
                "Fibras Alimentares (g)", "Sódio (mg)"
            ).contains(entry.getKey());

            if (!obrigatorio && valor == 0) continue;

            // Busca o Elemento correspondente pelo nome
            Elemento elemento = elementos.stream()
                .filter(e -> e.getEleNome().equalsIgnoreCase(entry.getKey()))
                .findFirst().orElse(null);

            if (elemento == null) continue;

            double vd = elemento.getEleValorRecomendado() > 0
                ? Math.round(valor / elemento.getEleValorRecomendado() * 100 * 10.0) / 10.0
                : 0;

            TabNutElemento tne = new TabNutElemento();
            tne.setTabelaNutricional(salva);
            tne.setElemento(elemento);
            tne.setTneValor(valor);
            tne.setTneValorPadrao(valor);
            tne.setTneVD(vd);
            tneList.add(tne);
        }

        tneRepo.saveAll(tneList);

        return "redirect:/tabela";
    }

    // --- helpers ---

    private Receita montarReceita(List<Map<String, Object>> ingredientes, double porcao) {
        Receita receita = new Receita();
        receita.setPorcaoG(porcao);
        List<IngredienteReceita> lista = new ArrayList<>();
        for (Map<String, Object> ing : ingredientes) {
            String nome = (String) ing.get("nome");
            double qtd = ((Number) ing.get("quantidade")).doubleValue();
            ItemTACO item = tacoService.buscarPorNome(nome);
            IngredienteReceita ir = new IngredienteReceita();
            ir.setNome(nome);
            ir.setQuantidadeG(qtd);
            ir.setItemTACO(item);
            lista.add(ir);
        }
        receita.setIngredientes(lista);
        return receita;
    }

    private Map<String, Object> nutrientesParaMap(ValoresNutricionais v, double pesoTotal) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("pesoTotal", pesoTotal);
        m.put("energiaPorcao", v.getEnergiaPorcao());
        m.put("energia100g", v.getEnergia100g());
        m.put("carboidratoPorcao", v.getCarboidratoPorcao());
        m.put("proteinaPorcao", v.getProteinaPorcao());
        m.put("lipideosPorcao", v.getLipideosPorcao());
        m.put("saturadoPorcao", v.getSaturadoPorcao());
        m.put("fibraPorcao", v.getFibraPorcao());
        m.put("sodioPorcao", v.getSodioPorcao());
        m.put("vdEnergia", v.getVDEnergia());
        m.put("vdCarb", v.getVDCarboidrato());
        m.put("vdProt", v.getVDProteina());
        m.put("vdLip", v.getVDLipideos());
        m.put("vdSat", v.getVDSaturado());
        m.put("vdFibra", v.getVDFibra());
        m.put("vdSodio", v.getVDSodio());
        return m;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> parsearIngredientesJson(String json) {
        // Parser simples para o JSON de ingredientes enviado pelo form
        // Formato esperado: [{"nome":"X","quantidade":Y},...]
        List<Map<String, Object>> lista = new ArrayList<>();
        json = json.trim().replaceAll("^\\[|\\]$", "");
        for (String obj : json.split("\\},\\{")) {
            obj = obj.replaceAll("[\\[\\]{}]", "").trim();
            Map<String, Object> map = new LinkedHashMap<>();
            for (String par : obj.split(",")) {
                String[] kv = par.split(":", 2);
                if (kv.length < 2) continue;
                String chave = kv[0].replaceAll("\"", "").trim();
                String valor = kv[1].replaceAll("\"", "").trim();
                if (chave.equals("quantidade")) {
                    try { map.put(chave, Double.parseDouble(valor)); }
                    catch (Exception e) { map.put(chave, 0.0); }
                } else {
                    map.put(chave, valor);
                }
            }
            if (!map.isEmpty()) lista.add(map);
        }
        return lista;
    }
}