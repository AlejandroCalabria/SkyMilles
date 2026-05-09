package com.NexGen.nutriiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.NexGen.nutriiftm.model.Receita;
import java.util.List;
@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    // SELECT * FROM receita WHERE nom_produto = ?
    List<Receita> findByNomeProduto(String nomeProduto);
}