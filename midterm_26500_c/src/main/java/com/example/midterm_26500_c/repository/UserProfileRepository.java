package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByNationalId(String nationalId);
}
