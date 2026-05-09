package com.NexGen.nutriiftm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.NexGen.nutriiftm.model.TabNutElemento;
import com.NexGen.nutriiftm.service.TabNutElementoService;
import com.NexGen.nutriiftm.service.TabelaNutricionalService;
import com.NexGen.nutriiftm.service.ElementoService;

@Controller
@RequestMapping("/nutrientes/valores")
@RequiredArgsConstructor
public class TabNutElementoController {

    private final TabNutElementoService tabNutService;
    private final TabelaNutricionalService tabelaService;
    private final ElementoService elementoService;

    @GetMapping("/inserir/{tabId}")
    public String formInserir(@PathVariable Long tabId, Model model) {
        model.addAttribute("tne", new TabNutElemento());
        model.addAttribute("tabela", tabelaService.buscarPorId(tabId));
        model.addAttribute("elementos", elementoService.listarTodos());
        return "inserirTabela";
    }

    @PostMapping("/salvar/{tabId}")
    public String salvar(@PathVariable Long tabId, TabNutElemento tne) {
        double porcao = tabelaService.buscarPorId(tabId).getTabPorcao();
        tabNutService.salvarComCalculo(tne, porcao);
        return "redirect:/tabela/imprimir/" + tabId;
    }

    @GetMapping("/alterar/{id}")
    public String formAlterar(@PathVariable Long id, Model model) {
        model.addAttribute("tne", tabNutService.buscarPorId(id));
        model.addAttribute("elementos", elementoService.listarTodos());
        return "alterarNutriente";
    }

    @PostMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        TabNutElemento tne = tabNutService.buscarPorId(id);
        Long tabId = tne.getTabelaNutricional().getTabCodigo();
        tabNutService.deletar(id);
        return "redirect:/tabela/imprimir/" + tabId;
    }
}