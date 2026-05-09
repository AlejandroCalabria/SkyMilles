package com.NexGen.nutriiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NexGen.nutriiftm.model.Fabricante;


@Repository
public interface FabricanteRepository extends JpaRepository<Fabricante, Long> {}
