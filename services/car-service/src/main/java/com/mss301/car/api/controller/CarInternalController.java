package com.mss301.car.api.controller;

import com.mss301.car.application.usecase.ConfirmRentedUseCase;
import com.mss301.car.application.usecase.LockCarUseCase;
import com.mss301.car.application.usecase.UnlockCarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/internal/cars")
@RequiredArgsConstructor
public class CarInternalController {

    private final LockCarUseCase lockCarUseCase;
    private final UnlockCarUseCase unlockCarUseCase;
    private final ConfirmRentedUseCase confirmRentedUseCase;

    @PostMapping("/{id}/lock")
    public ResponseEntity<Void> lockCar(@PathVariable UUID id) {
        lockCarUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/unlock")
    public ResponseEntity<Void> unlockCar(@PathVariable UUID id) {
        unlockCarUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/confirm-rented")
    public ResponseEntity<Void> confirmRented(@PathVariable UUID id) {
        confirmRentedUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
