package com.example.midterm_26500_c.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VillageRequest {

    @NotBlank(message = "Village code is required")
    private String code;

    @NotBlank(message = "Village name is required")
    private String name;

    @NotNull(message = "Cell ID is required")
    private Long cellId;
}
