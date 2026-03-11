package com.example.midterm_26500_c.mapper;

import com.example.midterm_26500_c.dto.response.CellResponse;
import com.example.midterm_26500_c.dto.response.DistrictResponse;
import com.example.midterm_26500_c.dto.response.ProvinceResponse;
import com.example.midterm_26500_c.dto.response.SectorResponse;
import com.example.midterm_26500_c.dto.response.VillageResponse;
import com.example.midterm_26500_c.entity.Cell;
import com.example.midterm_26500_c.entity.District;
import com.example.midterm_26500_c.entity.Province;
import com.example.midterm_26500_c.entity.Sector;
import com.example.midterm_26500_c.entity.Village;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public ProvinceResponse toProvinceResponse(Province province) {
        return ProvinceResponse.builder()
                .id(province.getId())
                .code(province.getCode())
                .name(province.getName())
                .build();
    }

    public DistrictResponse toDistrictResponse(District district) {
        return DistrictResponse.builder()
                .id(district.getId())
                .code(district.getCode())
                .name(district.getName())
                .provinceId(district.getProvince().getId())
                .provinceCode(district.getProvince().getCode())
                .provinceName(district.getProvince().getName())
                .build();
    }

    public SectorResponse toSectorResponse(Sector sector) {
        return SectorResponse.builder()
                .id(sector.getId())
                .code(sector.getCode())
                .name(sector.getName())
                .districtId(sector.getDistrict().getId())
                .districtCode(sector.getDistrict().getCode())
                .districtName(sector.getDistrict().getName())
                .build();
    }

    public CellResponse toCellResponse(Cell cell) {
        return CellResponse.builder()
                .id(cell.getId())
                .code(cell.getCode())
                .name(cell.getName())
                .sectorId(cell.getSector().getId())
                .sectorCode(cell.getSector().getCode())
                .sectorName(cell.getSector().getName())
                .build();
    }

    public VillageResponse toVillageResponse(Village village) {
        return VillageResponse.builder()
                .id(village.getId())
                .code(village.getCode())
                .name(village.getName())
                .cellId(village.getCell().getId())
                .cellCode(village.getCell().getCode())
                .cellName(village.getCell().getName())
                .build();
    }
}
