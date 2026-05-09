package com.NexGen.nutriiftm.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import com.NexGen.nutriiftm.model.TabNutElemento;
import com.NexGen.nutriiftm.repository.TabNutElementoRepository;

@Service
@RequiredArgsConstructor
public class TabNutElementoService {

    private final TabNutElementoRepository repo;
    private static final double TAB_VALOR = 100;

    public List<TabNutElemento> listarTodos() { return repo.findAll(); }
    public TabNutElemento buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    public void deletar(Long id) { repo.deleteById(id); }

    public TabNutElemento salvarComCalculo(TabNutElemento tne, double porcao) {
        tne.setTneValorPadrao(calcularValorPadrao(tne.getTneValor(), porcao));
        tne.setTneVD(calcularVD(
            tne.getTneValor(),
            tne.getElemento().getEleValorRecomendado()
        ));
        return repo.save(tne);
    }

    private double calcularValorPadrao(double valor, double porcao) {
        if (porcao != 0)
            return Math.round(valor * TAB_VALOR / porcao * 10.0) / 10.0;
        return 0;
    }

    private double calcularVD(double valor, double recomendado) {
        if (recomendado != 0)
            return Math.round(valor / recomendado * 100 * 10.0) / 10.0;
        return 0;
    }
}