package com.example.midterm_26500_c.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRequest {

    @NotBlank(message = "National ID is required")
    private String nationalId;

    private String bio;

    private LocalDate dateOfBirth;
}
