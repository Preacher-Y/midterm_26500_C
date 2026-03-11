package com.example.midterm_26500_c.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CellResponse {
    private Long id;
    private String code;
    private String name;
    private Long sectorId;
    private String sectorCode;
    private String sectorName;
}
