package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellRepository extends JpaRepository<Cell, Long> {
    boolean existsByCode(String code);
    boolean existsByName(String name);
}
