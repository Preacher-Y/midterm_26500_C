package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.SectorRequest;
import com.example.midterm_26500_c.dto.response.SectorResponse;
import com.example.midterm_26500_c.entity.District;
import com.example.midterm_26500_c.entity.Sector;
import com.example.midterm_26500_c.mapper.LocationMapper;
import com.example.midterm_26500_c.repository.DistrictRepository;
import com.example.midterm_26500_c.repository.SectorRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;
    private final DistrictRepository districtRepository;
    private final LocationMapper locationMapper;

    public SectorResponse create(SectorRequest request) throws BadRequestException {
        if (sectorRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Sector code already exists");
        }
        if (sectorRepository.existsByName(request.getName())) {
            throw new BadRequestException("Sector name already exists");
        }

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new NoSuchElementException("District not found with id: " + request.getDistrictId()));

        Sector sector = Sector.builder()
                .code(request.getCode())
                .name(request.getName())
                .district(district)
                .build();

        return locationMapper.toSectorResponse(sectorRepository.save(sector));
    }

    public List<SectorResponse> getAll() {
        return sectorRepository.findAll().stream().map(locationMapper::toSectorResponse).toList();
    }

    public SectorResponse getById(Long id) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sector not found with id: " + id));
        return locationMapper.toSectorResponse(sector);
    }

    public SectorResponse update(Long id, SectorRequest request) throws BadRequestException {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sector not found with id: " + id));

        if (!sector.getCode().equals(request.getCode()) && sectorRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Sector code already exists");
        }
        if (!sector.getName().equals(request.getName()) && sectorRepository.existsByName(request.getName())) {
            throw new BadRequestException("Sector name already exists");
        }

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new NoSuchElementException("District not found with id: " + request.getDistrictId()));

        sector.setCode(request.getCode());
        sector.setName(request.getName());
        sector.setDistrict(district);

        return locationMapper.toSectorResponse(sectorRepository.save(sector));
    }

    public void delete(Long id) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sector not found with id: " + id));
        sectorRepository.delete(sector);
    }
}
