package com.mss301.car.api.controller;

import com.mss301.car.api.dto.CarDto;
import com.mss301.car.api.dto.CarDetailDto;
import com.mss301.car.application.usecase.GetCarListUseCase;
import com.mss301.car.application.usecase.GetCarDetailUseCase;
import com.mss301.car.api.dto.CarSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/cars")
@RequiredArgsConstructor
public class CarPublicController {

    // Controller chỉ gọi tới UseCase, không gọi trực tiếp Repository
    private final GetCarListUseCase getCarListUseCase;
    private final GetCarDetailUseCase getCarDetailUseCase;

    @GetMapping
    public ResponseEntity<Page<CarDto>> getAllCars(CarSearchCriteria criteria, Pageable pageable) {
        Page<CarDto> cars = getCarListUseCase.execute(criteria, pageable);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDetailDto> getCarDetail(@PathVariable UUID id) {
        CarDetailDto carDetail = getCarDetailUseCase.execute(id);
        return ResponseEntity.ok(carDetail);
    }
}
