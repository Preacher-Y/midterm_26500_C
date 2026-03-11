package com.example.midterm_26500_c.controller;

import com.example.midterm_26500_c.dto.request.SectorRequest;
import com.example.midterm_26500_c.dto.response.SectorResponse;
import com.example.midterm_26500_c.service.SectorService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @PostMapping
    public ResponseEntity<SectorResponse> create(@Valid @RequestBody SectorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SectorResponse>> getAll() {
        return ResponseEntity.ok(sectorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sectorService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectorResponse> update(@PathVariable Long id, @Valid @RequestBody SectorRequest request) {
        return ResponseEntity.ok(sectorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
