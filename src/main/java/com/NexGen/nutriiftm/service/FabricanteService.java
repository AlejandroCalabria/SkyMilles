package com.NexGen.nutriiftm.service;

import org.springframework.stereotype.Service;

import com.NexGen.nutriiftm.model.Fabricante;

import java.util.List;
import com.NexGen.nutriiftm.repository.FabricanteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FabricanteService {
    private final FabricanteRepository repo;

    public List<Fabricante> listarTodos() { return repo.findAll(); }
    public Fabricante buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    public Fabricante salvar(Fabricante f) { return repo.save(f); }
    public void deletar(Long id) { repo.deleteById(id); }
}
