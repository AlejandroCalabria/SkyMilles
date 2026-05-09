package com.NexGen.nutriiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NexGen.nutriiftm.model.Cooperativa;

@Repository
public interface CooperativaRepository extends JpaRepository<Cooperativa, Long> {}
