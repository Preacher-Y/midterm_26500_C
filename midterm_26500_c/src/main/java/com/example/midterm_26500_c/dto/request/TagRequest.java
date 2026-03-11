package com.example.midterm_26500_c.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagRequest {

    @NotBlank(message = "Tag name is required")
    private String name;
}
