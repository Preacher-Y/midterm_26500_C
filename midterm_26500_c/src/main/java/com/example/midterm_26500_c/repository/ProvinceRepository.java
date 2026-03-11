package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.Province;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    boolean existsByCode(String code);
    boolean existsByName(String name);
    Optional<Province> findByCode(String code);
    Optional<Province> findByName(String name);
}
