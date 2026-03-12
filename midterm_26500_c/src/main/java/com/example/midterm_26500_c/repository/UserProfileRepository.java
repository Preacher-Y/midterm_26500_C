package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.UserProfile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    boolean existsByNationalId(String nationalId);
}
