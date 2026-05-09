package com.NexGen.nutriiftm.service;

import org.springframework.stereotype.Service;
import com.NexGen.nutriiftm.model.UnidadeMedida;
import java.util.List;
import com.NexGen.nutriiftm.repository.UnidadeMedidaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadeMedidaService {
    private final UnidadeMedidaRepository repo;
    public List<UnidadeMedida> listarTodos() { return repo.findAll(); }
    public UnidadeMedida buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    public UnidadeMedida salvar(UnidadeMedida u) { return repo.save(u); }
    public void deletar(Long id) { repo.deleteById(id); }
}
