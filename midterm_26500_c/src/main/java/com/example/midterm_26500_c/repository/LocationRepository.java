package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.Location;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.midterm_26500_c.enums.LocationType;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    boolean existsByCode(String code);
    boolean existsByTypeAndParent_IdAndName(LocationType type, UUID parentId, String name);
    boolean existsByTypeAndParentIsNullAndName(LocationType type, String name);
    Optional<Location> findByCode(String code);
    List<Location> findByName(String name);
}
