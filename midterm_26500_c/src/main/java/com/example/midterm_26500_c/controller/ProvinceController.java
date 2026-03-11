package com.example.midterm_26500_c.controller;

import com.example.midterm_26500_c.dto.request.ProvinceRequest;
import com.example.midterm_26500_c.dto.response.ProvinceResponse;
import com.example.midterm_26500_c.service.ProvinceService;
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
@RequestMapping("/api/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;

    @PostMapping
    public ResponseEntity<ProvinceResponse> create(@Valid @RequestBody ProvinceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(provinceService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ProvinceResponse>> getAll() {
        return ResponseEntity.ok(provinceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(provinceService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinceResponse> update(@PathVariable Long id, @Valid @RequestBody ProvinceRequest request) {
        return ResponseEntity.ok(provinceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        provinceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
