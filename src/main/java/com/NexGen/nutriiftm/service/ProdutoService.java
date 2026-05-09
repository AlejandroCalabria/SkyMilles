package com.NexGen.nutriiftm.service;

import org.springframework.stereotype.Service;

import com.NexGen.nutriiftm.model.Produto;
import com.NexGen.nutriiftm.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repo;

    public List<Produto> listarTodos() { return repo.findAll(); }
    public Produto buscarPorId(Long id) { return repo.findById(id).orElseThrow(); }
    public Produto salvar(Produto p) { return repo.save(p); }
    public void deletar(Long id) { repo.deleteById(id); }

}
