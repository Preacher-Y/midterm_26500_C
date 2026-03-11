package com.example.midterm_26500_c.controller;

import com.example.midterm_26500_c.dto.request.CellRequest;
import com.example.midterm_26500_c.dto.response.CellResponse;
import com.example.midterm_26500_c.service.CellService;
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
@RequestMapping("/api/cells")
@RequiredArgsConstructor
public class CellController {

    private final CellService cellService;

    @PostMapping
    public ResponseEntity<CellResponse> create(@Valid @RequestBody CellRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cellService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CellResponse>> getAll() {
        return ResponseEntity.ok(cellService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CellResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cellService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CellResponse> update(@PathVariable Long id, @Valid @RequestBody CellRequest request) {
        return ResponseEntity.ok(cellService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cellService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
