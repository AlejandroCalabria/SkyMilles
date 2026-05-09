package com.NexGen.nutriiftm.service;

import org.springframework.stereotype.Service;

import com.NexGen.nutriiftm.model.Cooperativa;
import com.NexGen.nutriiftm.repository.CooperativaRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CooperativaService {
    private final CooperativaRepository repo;

    public List<Cooperativa> listarTodos() { return repo.findAll(); }
    public Cooperativa buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    public Cooperativa salvar(Cooperativa c) { return repo.save(c); }
    public void deletar(Long id) { repo.deleteById(id); }
}
