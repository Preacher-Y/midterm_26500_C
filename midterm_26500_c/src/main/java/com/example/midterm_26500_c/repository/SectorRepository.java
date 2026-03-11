package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    boolean existsByCode(String code);
    boolean existsByName(String name);
}
