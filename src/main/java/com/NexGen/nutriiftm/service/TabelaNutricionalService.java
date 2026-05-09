package com.NexGen.nutriiftm.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.NexGen.nutriiftm.repository.TabelaNutricionalRepository;
import com.NexGen.nutriiftm.model.TabelaNutricional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TabelaNutricionalService {

    private final TabelaNutricionalRepository repo;

    public List<TabelaNutricional> listarTodos() {
        return repo.findAllComElementos(); // ← era findAll()
    }

    public TabelaNutricional buscarPorId(Long id) {
        return repo.findByIdComElementos(id).orElseThrow(); // ← era findById()
    }

    public TabelaNutricional salvar(TabelaNutricional t) { return repo.save(t); }
    public void deletar(Long id) { repo.deleteById(id); }

    public byte[] gerarPdf(Long id) throws Exception {
        throw new UnsupportedOperationException("Geração de PDF ainda não implementada");
    }
}