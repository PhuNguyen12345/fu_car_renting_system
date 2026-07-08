package com.mss301.car.api.controller;
import com.mss301.car.api.dto.LocationDto;
import com.mss301.car.application.usecase.GetLocationListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public/locations")
@RequiredArgsConstructor
public class LocationPublicController {
    private final GetLocationListUseCase useCase;

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok(useCase.execute());
    }
}
