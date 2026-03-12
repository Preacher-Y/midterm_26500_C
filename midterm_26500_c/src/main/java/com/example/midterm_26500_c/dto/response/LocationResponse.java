package com.example.midterm_26500_c.dto.response;

import com.example.midterm_26500_c.enums.LocationType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationResponse {
    private UUID id;
    private String code;
    private String name;
    private LocationType type;
    private UUID parentId;
    private String parentCode;
    private String parentName;
}
