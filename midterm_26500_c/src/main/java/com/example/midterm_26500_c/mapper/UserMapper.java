package com.example.midterm_26500_c.mapper;

import com.example.midterm_26500_c.dto.response.UserProfileResponse;
import com.example.midterm_26500_c.dto.response.UserResponse;
import com.example.midterm_26500_c.entity.Location;
import com.example.midterm_26500_c.enums.LocationType;
import com.example.midterm_26500_c.entity.User;
import com.example.midterm_26500_c.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        Location province = findByType(user.getLocation(), LocationType.PROVINCE);
        Location district = findByType(user.getLocation(), LocationType.DISTRICT);
        Location sector = findByType(user.getLocation(), LocationType.SECTOR);
        Location cell = findByType(user.getLocation(), LocationType.CELL);
        Location village = findByType(user.getLocation(), LocationType.VILLAGE);

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .locationId(user.getLocation().getId())
                .locationCode(user.getLocation().getCode())
                .locationName(user.getLocation().getName())
                .locationType(user.getLocation().getType())
                .provinceName(province != null ? province.getName() : null)
                .districtName(district != null ? district.getName() : null)
                .sectorName(sector != null ? sector.getName() : null)
                .cellName(cell != null ? cell.getName() : null)
                .villageName(village != null ? village.getName() : null)
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

    private Location findByType(Location location, LocationType type) {
        Location current = location;
        while (current != null) {
            if (current.getType() == type) {
                return current;
            }
            current = current.getParent();
        }
        return null;
    }
}
