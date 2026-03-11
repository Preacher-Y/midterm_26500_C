package com.example.midterm_26500_c.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {
    private Long id;
    private String nationalId;
    private String bio;
    private LocalDate dateOfBirth;
}
