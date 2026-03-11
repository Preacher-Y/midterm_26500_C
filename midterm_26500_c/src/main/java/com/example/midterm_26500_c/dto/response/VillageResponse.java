package com.example.midterm_26500_c.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VillageResponse {
    private Long id;
    private String code;
    private String name;
    private Long cellId;
    private String cellCode;
    private String cellName;
}
