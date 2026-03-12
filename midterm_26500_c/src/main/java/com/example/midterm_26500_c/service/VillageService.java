package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.VillageRequest;
import com.example.midterm_26500_c.dto.response.VillageResponse;
import com.example.midterm_26500_c.entity.Cell;
import com.example.midterm_26500_c.entity.Village;
import com.example.midterm_26500_c.mapper.LocationMapper;
import com.example.midterm_26500_c.repository.CellRepository;
import com.example.midterm_26500_c.repository.VillageRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VillageService {

    private final VillageRepository villageRepository;
    private final CellRepository cellRepository;
    private final LocationMapper locationMapper;

    public VillageResponse create(VillageRequest request) throws BadRequestException {
        if (villageRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Village code already exists");
        }
        if (villageRepository.existsByName(request.getName())) {
            throw new BadRequestException("Village name already exists");
        }

        Cell cell = cellRepository.findById(request.getCellId())
                .orElseThrow(() -> new NoSuchElementException("Cell not found with id: " + request.getCellId()));

        Village village = Village.builder()
                .code(request.getCode())
                .name(request.getName())
                .cell(cell)
                .build();

        return locationMapper.toVillageResponse(villageRepository.save(village));
    }

    public List<VillageResponse> getAll() {
        return villageRepository.findAll().stream().map(locationMapper::toVillageResponse).toList();
    }

    public VillageResponse getById(Long id) {
        Village village = villageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Village not found with id: " + id));
        return locationMapper.toVillageResponse(village);
    }

    public VillageResponse update(Long id, VillageRequest request) throws BadRequestException {
        Village village = villageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Village not found with id: " + id));

        if (!village.getCode().equals(request.getCode()) && villageRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Village code already exists");
        }
        if (!village.getName().equals(request.getName()) && villageRepository.existsByName(request.getName())) {
            throw new BadRequestException("Village name already exists");
        }

        Cell cell = cellRepository.findById(request.getCellId())
                .orElseThrow(() -> new NoSuchElementException("Cell not found with id: " + request.getCellId()));

        village.setCode(request.getCode());
        village.setName(request.getName());
        village.setCell(cell);

        return locationMapper.toVillageResponse(villageRepository.save(village));
    }

    public void delete(Long id) {
        Village village = villageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Village not found with id: " + id));
        villageRepository.delete(village);
    }
}
