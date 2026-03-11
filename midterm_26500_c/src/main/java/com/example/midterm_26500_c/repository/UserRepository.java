package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    List<User> findByVillage_Cell_Sector_District_Province_Code(String provinceCode);
    List<User> findByVillage_Cell_Sector_District_Province_Name(String provinceName);
}
