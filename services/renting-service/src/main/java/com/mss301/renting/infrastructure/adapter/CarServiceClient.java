package com.mss301.renting.infrastructure.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.mss301.renting.api.dto.CarDetailDto;

import java.util.UUID;

@FeignClient(name = "car-service")
public interface CarServiceClient {

    @GetMapping("/api/v1/public/cars/{id}")
    CarDetailDto getCarDetail(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/internal/cars/{id}/lock")
    void lockCar(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/internal/cars/{id}/unlock")
    void unlockCar(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/internal/cars/{id}/confirm-rented")
    void confirmRentedCar(@PathVariable("id") UUID id);
}
