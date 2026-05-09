package com.NexGen.nutriiftm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NexGen.nutriiftm.model.Cooperativa;
import com.NexGen.nutriiftm.service.CooperativaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cooperativas")
@RequiredArgsConstructor
public class CooperativaController {
    private final CooperativaService service;

    @GetMapping
    public String listar(Model model){
        model.addAttribute("cooperativas", service.listarTodos());
        return "cooperativas";
    }

    @GetMapping("/alterar/{id}")
    public String formAlterar(@PathVariable Long id, Model model){
        model.addAttribute("cooperativa", service.buscarPorId(id));
        return "alterarCooperativa";
    }
    @PostMapping("/salvar")
    public String salvar(Cooperativa cooperativa){
        service.salvar(cooperativa);
        return "redirect:/cooperativas";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id){
        service.deletar(id);
        return "redirect:/cooperativas";
    }
}