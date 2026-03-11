package com.example.midterm_26500_c.controller;

import com.example.midterm_26500_c.dto.request.DistrictRequest;
import com.example.midterm_26500_c.dto.response.DistrictResponse;
import com.example.midterm_26500_c.service.DistrictService;
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
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping
    public ResponseEntity<DistrictResponse> create(@Valid @RequestBody DistrictRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(districtService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<DistrictResponse>> getAll() {
        return ResponseEntity.ok(districtService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistrictResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(districtService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistrictResponse> update(@PathVariable Long id, @Valid @RequestBody DistrictRequest request) {
        return ResponseEntity.ok(districtService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        districtService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
