package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.CarDetailDto;
import com.mss301.car.domain.entity.Car;
import com.mss301.car.domain.repository.ICarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCarDetailUseCase {

    private final ICarRepository carRepository;

    public CarDetailDto execute(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy xe với ID: " + id)); 
                // Tương lai có thể tạo ResourceNotFoundException riêng

        return CarDetailDto.fromEntity(car);
    }
}
