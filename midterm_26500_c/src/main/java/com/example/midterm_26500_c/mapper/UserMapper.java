package com.example.midterm_26500_c.mapper;

import com.example.midterm_26500_c.dto.response.UserProfileResponse;
import com.example.midterm_26500_c.dto.response.UserResponse;
import com.example.midterm_26500_c.entity.User;
import com.example.midterm_26500_c.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .villageCode(user.getVillage().getCode())
                .villageName(user.getVillage().getName())
                .cellName(user.getVillage().getCell().getName())
                .sectorName(user.getVillage().getCell().getSector().getName())
                .districtName(user.getVillage().getCell().getSector().getDistrict().getName())
                .provinceCode(user.getVillage().getCell().getSector().getDistrict().getProvince().getCode())
                .provinceName(user.getVillage().getCell().getSector().getDistrict().getProvince().getName())
                .profile(toUserProfileResponse(user.getProfile()))
                .build();
    }

    public UserProfileResponse toUserProfileResponse(UserProfile profile) {
        if (profile == null) {
            return null;
        }
        return UserProfileResponse.builder()
                .id(profile.getId())
                .nationalId(profile.getNationalId())
                .bio(profile.getBio())
                .dateOfBirth(profile.getDateOfBirth())
                .build();
    }
}
