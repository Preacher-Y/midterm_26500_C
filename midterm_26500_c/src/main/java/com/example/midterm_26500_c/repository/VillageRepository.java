package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.Village;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VillageRepository extends JpaRepository<Village, Long> {
    boolean existsByCode(String code);
    boolean existsByName(String name);
    Optional<Village> findByCode(String code);
    Optional<Village> findByName(String name);
}
