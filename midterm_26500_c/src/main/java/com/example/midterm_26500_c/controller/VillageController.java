package com.example.midterm_26500_c.controller;

import com.example.midterm_26500_c.dto.request.VillageRequest;
import com.example.midterm_26500_c.dto.response.VillageResponse;
import com.example.midterm_26500_c.service.VillageService;
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
@RequestMapping("/api/villages")
@RequiredArgsConstructor
public class VillageController {

    private final VillageService villageService;

    @PostMapping
    public ResponseEntity<VillageResponse> create(@Valid @RequestBody VillageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(villageService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<VillageResponse>> getAll() {
        return ResponseEntity.ok(villageService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VillageResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(villageService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VillageResponse> update(@PathVariable Long id, @Valid @RequestBody VillageRequest request) {
        return ResponseEntity.ok(villageService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        villageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
