package com.example.midterm_26500_c.dto.response;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {
    private UUID id;
    private String nationalId;
    private String bio;
    private LocalDate dateOfBirth;
}
