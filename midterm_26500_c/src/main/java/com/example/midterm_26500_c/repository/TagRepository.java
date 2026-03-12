package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.Tag;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    boolean existsByName(String name);
    Optional<Tag> findByName(String name);
}
