package com.example.midterm_26500_c.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectorRequest {

    @NotBlank(message = "Sector code is required")
    private String code;

    @NotBlank(message = "Sector name is required")
    private String name;

    @NotNull(message = "District ID is required")
    private Long districtId;
}
