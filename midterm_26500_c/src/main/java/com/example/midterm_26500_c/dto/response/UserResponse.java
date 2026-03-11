package com.example.midterm_26500_c.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String villageCode;
    private String villageName;
    private String cellName;
    private String sectorName;
    private String districtName;
    private String provinceCode;
    private String provinceName;
    private UserProfileResponse profile;
}
