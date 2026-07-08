package com.mss301.car.api.controller;
import com.mss301.car.api.dto.BrandDto;
import com.mss301.car.application.usecase.GetBrandListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public/brands")
@RequiredArgsConstructor
public class BrandPublicController {
    private final GetBrandListUseCase useCase;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        return ResponseEntity.ok(useCase.execute());
    }
}
