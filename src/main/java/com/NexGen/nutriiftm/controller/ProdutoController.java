package com.NexGen.nutriiftm.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.NexGen.nutriiftm.model.Produto;
import com.NexGen.nutriiftm.service.FabricanteService;
import com.NexGen.nutriiftm.service.ProdutoService;

import lombok.RequiredArgsConstructor;
@Controller
@RequestMapping("/produtos")
@RequiredArgsConstructor

public class ProdutoController {

    private final ProdutoService produtoService;
    private final FabricanteService fabricanteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "produtos";
    }

    @GetMapping("/inserir")
    public String formInserir(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("fabricantes", fabricanteService.listarTodos());
        return "inserirProduto";
    }

    @GetMapping("/alterar/{id}")
    public String formAlterar(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoService.buscarPorId(id));
        model.addAttribute("fabricantes", fabricanteService.listarTodos());
        return "alterarProduto";
    }

    @PostMapping("/salvar")
    public String salvar(Produto produto) {
        produtoService.salvar(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/remover/{id}")
    public String formRemover(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoService.buscarPorId(id));
        return "removerProduto";
    }

    @PostMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        produtoService.deletar(id);
        return "redirect:/produtos";
    }
}