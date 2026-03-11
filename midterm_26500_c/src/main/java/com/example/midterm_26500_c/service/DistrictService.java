package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.DistrictRequest;
import com.example.midterm_26500_c.dto.response.DistrictResponse;
import com.example.midterm_26500_c.entity.District;
import com.example.midterm_26500_c.entity.Province;
import com.example.midterm_26500_c.mapper.LocationMapper;
import com.example.midterm_26500_c.repository.DistrictRepository;
import com.example.midterm_26500_c.repository.ProvinceRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final LocationMapper locationMapper;

    public DistrictResponse create(DistrictRequest request) {
        if (districtRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("District code already exists");
        }
        if (districtRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("District name already exists");
        }

        Province province = provinceRepository.findById(request.getProvinceId())
                .orElseThrow(() -> new NoSuchElementException("Province not found with id: " + request.getProvinceId()));

        District district = District.builder()
                .code(request.getCode())
                .name(request.getName())
                .province(province)
                .build();

        return locationMapper.toDistrictResponse(districtRepository.save(district));
    }

    public List<DistrictResponse> getAll() {
        return districtRepository.findAll().stream().map(locationMapper::toDistrictResponse).toList();
    }

    public DistrictResponse getById(Long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("District not found with id: " + id));
        return locationMapper.toDistrictResponse(district);
    }

    public DistrictResponse update(Long id, DistrictRequest request) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("District not found with id: " + id));

        if (!district.getCode().equals(request.getCode()) && districtRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("District code already exists");
        }
        if (!district.getName().equals(request.getName()) && districtRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("District name already exists");
        }

        Province province = provinceRepository.findById(request.getProvinceId())
                .orElseThrow(() -> new NoSuchElementException("Province not found with id: " + request.getProvinceId()));

        district.setCode(request.getCode());
        district.setName(request.getName());
        district.setProvince(province);

        return locationMapper.toDistrictResponse(districtRepository.save(district));
    }

    public void delete(Long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("District not found with id: " + id));
        districtRepository.delete(district);
    }
}
