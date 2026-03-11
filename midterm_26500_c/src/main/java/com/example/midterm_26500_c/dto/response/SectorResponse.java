package com.example.midterm_26500_c.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SectorResponse {
    private Long id;
    private String code;
    private String name;
    private Long districtId;
    private String districtCode;
    private String districtName;
}
