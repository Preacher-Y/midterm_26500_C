package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    List<User> findByLocation_IdOrLocation_Parent_IdOrLocation_Parent_Parent_IdOrLocation_Parent_Parent_Parent_IdOrLocation_Parent_Parent_Parent_Parent_Id(
            UUID id,
            UUID parentId,
            UUID grandParentId,
            UUID greatGrandParentId,
            UUID fourthAncestorId
    );
}
