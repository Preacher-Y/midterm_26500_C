package com.example.midterm_26500_c.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinceRequest {

    @NotBlank(message = "Province code is required")
    private String code;

    @NotBlank(message = "Province name is required")
    private String name;
}
