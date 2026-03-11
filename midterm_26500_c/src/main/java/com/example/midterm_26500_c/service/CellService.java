package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.CellRequest;
import com.example.midterm_26500_c.dto.response.CellResponse;
import com.example.midterm_26500_c.entity.Cell;
import com.example.midterm_26500_c.entity.Sector;
import com.example.midterm_26500_c.mapper.LocationMapper;
import com.example.midterm_26500_c.repository.CellRepository;
import com.example.midterm_26500_c.repository.SectorRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CellService {

    private final CellRepository cellRepository;
    private final SectorRepository sectorRepository;
    private final LocationMapper locationMapper;

    public CellResponse create(CellRequest request) {
        if (cellRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Cell code already exists");
        }
        if (cellRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Cell name already exists");
        }

        Sector sector = sectorRepository.findById(request.getSectorId())
                .orElseThrow(() -> new NoSuchElementException("Sector not found with id: " + request.getSectorId()));

        Cell cell = Cell.builder()
                .code(request.getCode())
                .name(request.getName())
                .sector(sector)
                .build();

        return locationMapper.toCellResponse(cellRepository.save(cell));
    }

    public List<CellResponse> getAll() {
        return cellRepository.findAll().stream().map(locationMapper::toCellResponse).toList();
    }

    public CellResponse getById(Long id) {
        Cell cell = cellRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cell not found with id: " + id));
        return locationMapper.toCellResponse(cell);
    }

    public CellResponse update(Long id, CellRequest request) {
        Cell cell = cellRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cell not found with id: " + id));

        if (!cell.getCode().equals(request.getCode()) && cellRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Cell code already exists");
        }
        if (!cell.getName().equals(request.getName()) && cellRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Cell name already exists");
        }

        Sector sector = sectorRepository.findById(request.getSectorId())
                .orElseThrow(() -> new NoSuchElementException("Sector not found with id: " + request.getSectorId()));

        cell.setCode(request.getCode());
        cell.setName(request.getName());
        cell.setSector(sector);

        return locationMapper.toCellResponse(cellRepository.save(cell));
    }

    public void delete(Long id) {
        Cell cell = cellRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cell not found with id: " + id));
        cellRepository.delete(cell);
    }
}
