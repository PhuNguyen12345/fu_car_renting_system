package com.mss301.car.api.controller;

import com.mss301.car.api.dto.AdminCarSearchCriteria;
import com.mss301.car.api.dto.CarDto;
import com.mss301.car.api.dto.CreateCarDto;
import com.mss301.car.api.dto.UpdateCarDto;
import com.mss301.car.api.dto.UpdateCarStatusDto;
import com.mss301.car.api.dto.CloudinarySignatureDto;
import com.mss301.car.application.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/cars")
@RequiredArgsConstructor
public class CarAdminController {

    private final GetAdminCarListUseCase getAdminCarListUseCase;
    private final CreateCarUseCase createCarUseCase;
    private final UpdateCarUseCase updateCarUseCase;
    private final ChangeCarStatusUseCase changeCarStatusUseCase;
    private final DeleteCarUseCase deleteCarUseCase;
    private final RestoreCarUseCase restoreCarUseCase;
    private final GenerateCloudinarySignatureUseCase generateCloudinarySignatureUseCase;

    @GetMapping
    public ResponseEntity<Page<CarDto>> getAllCarsForAdmin(AdminCarSearchCriteria criteria, Pageable pageable) {
        return ResponseEntity.ok(getAdminCarListUseCase.execute(criteria, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> createCar(@RequestBody CreateCarDto dto) {
        createCarUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable UUID id, @RequestBody UpdateCarDto dto) {
        updateCarUseCase.execute(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeCarStatus(@PathVariable UUID id, @RequestBody UpdateCarStatusDto dto) {
        changeCarStatusUseCase.execute(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        deleteCarUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<Void> restoreCar(@PathVariable UUID id) {
        restoreCarUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cloudinary-signature")
    public ResponseEntity<CloudinarySignatureDto> getCloudinarySignature() {
        return ResponseEntity.ok(generateCloudinarySignatureUseCase.execute());
    }
}
