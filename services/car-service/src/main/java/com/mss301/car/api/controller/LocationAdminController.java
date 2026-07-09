package com.mss301.car.api.controller;

import com.mss301.car.api.dto.LocationDto;
import com.mss301.car.api.dto.LocationRequestDto;
import com.mss301.car.api.dto.UpdateLocationRequestDto;
import com.mss301.car.application.usecase.CreateLocationUseCase;
import com.mss301.car.application.usecase.DeleteLocationUseCase;
import com.mss301.car.application.usecase.GetLocationListUseCase;
import com.mss301.car.application.usecase.GetLocationUseCase;
import com.mss301.car.application.usecase.UpdateLocationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/locations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class LocationAdminController {

    private final GetLocationListUseCase getLocationListUseCase;
    private final GetLocationUseCase getLocationUseCase;
    private final CreateLocationUseCase createLocationUseCase;
    private final UpdateLocationUseCase updateLocationUseCase;
    private final DeleteLocationUseCase deleteLocationUseCase;

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok(getLocationListUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable UUID id) {
        return ResponseEntity.ok(getLocationUseCase.execute(id));
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationRequestDto request) {
        return ResponseEntity.ok(createLocationUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(
            @PathVariable UUID id,
            @RequestBody UpdateLocationRequestDto request) {
        return ResponseEntity.ok(updateLocationUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID id) {
        deleteLocationUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
