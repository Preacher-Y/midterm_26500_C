package com.example.midterm_26500_c.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagResponse {
    private UUID id;
    private String name;
}
