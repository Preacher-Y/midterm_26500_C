package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
    boolean existsByCode(String code);
    boolean existsByName(String name);
}
