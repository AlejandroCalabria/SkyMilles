package com.NexGen.nutriiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.NexGen.nutriiftm.model.IngredienteReceita;
@Repository
public interface IngredienteReceitaRepository extends JpaRepository<IngredienteReceita, Long> {}
