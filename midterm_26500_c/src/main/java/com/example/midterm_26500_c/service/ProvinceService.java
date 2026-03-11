package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.ProvinceRequest;
import com.example.midterm_26500_c.dto.response.ProvinceResponse;
import com.example.midterm_26500_c.entity.Province;
import com.example.midterm_26500_c.mapper.LocationMapper;
import com.example.midterm_26500_c.repository.ProvinceRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final LocationMapper locationMapper;

    public ProvinceResponse create(ProvinceRequest request) {
        if (provinceRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Province code already exists");
        }
        if (provinceRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Province name already exists");
        }

        Province province = Province.builder()
                .code(request.getCode())
                .name(request.getName())
                .build();

        return locationMapper.toProvinceResponse(provinceRepository.save(province));
    }

    public List<ProvinceResponse> getAll() {
        return provinceRepository.findAll().stream().map(locationMapper::toProvinceResponse).toList();
    }

    public ProvinceResponse getById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Province not found with id: " + id));
        return locationMapper.toProvinceResponse(province);
    }

    public ProvinceResponse update(Long id, ProvinceRequest request) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Province not found with id: " + id));

        if (!province.getCode().equals(request.getCode()) && provinceRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Province code already exists");
        }
        if (!province.getName().equals(request.getName()) && provinceRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Province name already exists");
        }

        province.setCode(request.getCode());
        province.setName(request.getName());

        return locationMapper.toProvinceResponse(provinceRepository.save(province));
    }

    public void delete(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Province not found with id: " + id));
        provinceRepository.delete(province);
    }
}
