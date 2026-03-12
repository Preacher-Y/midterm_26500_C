package com.example.midterm_26500_c.mapper;

import com.example.midterm_26500_c.dto.response.LocationResponse;
import com.example.midterm_26500_c.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationResponse toLocationResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .code(location.getCode())
                .name(location.getName())
                .type(location.getType())
                .parentId(location.getParent() != null ? location.getParent().getId() : null)
                .parentCode(location.getParent() != null ? location.getParent().getCode() : null)
                .parentName(location.getParent() != null ? location.getParent().getName() : null)
                .build();
    }
}
