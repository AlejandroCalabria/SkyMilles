package com.NexGen.nutriiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NexGen.nutriiftm.model.Elemento;

public interface ElementoRepository extends JpaRepository<Elemento, Long> {

    boolean existsByEleOrdem(int eleOrdem);

    boolean existsByEleOrdemAndEleCodigoNot(int eleOrdem, Long eleCodigo);

}