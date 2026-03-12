package com.example.midterm_26500_c.dto.response;

import com.example.midterm_26500_c.enums.LocationType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UUID locationId;
    private String locationCode;
    private String locationName;
    private LocationType locationType;
    private String provinceName;
    private String districtName;
    private String sectorName;
    private String cellName;
    private String villageName;
    private UserProfileResponse profile;
}
