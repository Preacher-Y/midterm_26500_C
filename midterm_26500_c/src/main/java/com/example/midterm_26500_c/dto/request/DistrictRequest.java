package com.example.midterm_26500_c.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictRequest {

    @NotBlank(message = "District code is required")
    private String code;

    @NotBlank(message = "District name is required")
    private String name;

    @NotNull(message = "Province ID is required")
    private Long provinceId;
}
