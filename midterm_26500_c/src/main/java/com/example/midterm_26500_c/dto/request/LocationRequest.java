package com.example.midterm_26500_c.dto.request;

import com.example.midterm_26500_c.enums.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {

    @NotBlank(message = "Location code is required")
    private String code;

    @NotBlank(message = "Location name is required")
    private String name;

    @NotNull(message = "Location type is required")
    private LocationType type;

    private UUID parentId;
}
