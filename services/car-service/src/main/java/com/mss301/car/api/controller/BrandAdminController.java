package com.mss301.car.api.controller;

import com.mss301.car.api.dto.BrandDto;
import com.mss301.car.api.dto.BrandRequestDto;
import com.mss301.car.api.dto.UpdateBrandRequestDto;
import com.mss301.car.application.usecase.CreateBrandUseCase;
import com.mss301.car.application.usecase.DeleteBrandUseCase;
import com.mss301.car.application.usecase.GetBrandListUseCase;
import com.mss301.car.application.usecase.GetBrandUseCase;
import com.mss301.car.application.usecase.UpdateBrandUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/brands")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class BrandAdminController {

    private final GetBrandListUseCase getBrandListUseCase;
    private final GetBrandUseCase getBrandUseCase;
    private final CreateBrandUseCase createBrandUseCase;
    private final UpdateBrandUseCase updateBrandUseCase;
    private final DeleteBrandUseCase deleteBrandUseCase;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        return ResponseEntity.ok(getBrandListUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrand(@PathVariable UUID id) {
        return ResponseEntity.ok(getBrandUseCase.execute(id));
    }

    @PostMapping
    public ResponseEntity<BrandDto> createBrand(@Valid @RequestBody BrandRequestDto request) {
        return ResponseEntity.ok(createBrandUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BrandDto> updateBrand(
            @PathVariable UUID id,
            @RequestBody UpdateBrandRequestDto request) {
        return ResponseEntity.ok(updateBrandUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        deleteBrandUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
