package com.NexGen.nutriiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.NexGen.nutriiftm.model.TabNutElemento;
@Repository
public interface TabNutElementoRepository extends JpaRepository<TabNutElemento, Long> {

}
