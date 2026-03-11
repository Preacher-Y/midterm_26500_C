package com.example.midterm_26500_c.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellRequest {

    @NotBlank(message = "Cell code is required")
    private String code;

    @NotBlank(message = "Cell name is required")
    private String name;

    @NotNull(message = "Sector ID is required")
    private Long sectorId;
}
