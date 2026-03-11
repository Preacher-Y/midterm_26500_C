package com.example.midterm_26500_c.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistrictResponse {
    private Long id;
    private String code;
    private String name;
    private Long provinceId;
    private String provinceCode;
    private String provinceName;
}
