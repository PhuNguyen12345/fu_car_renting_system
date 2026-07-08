package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.UpdateCarStatusDto;
import com.mss301.car.domain.aggregate.CarAggregate;
import com.mss301.car.domain.entity.Car;
import com.mss301.car.domain.repository.ICarRepository;
import com.mss301.car.domain.vo.CarStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangeCarStatusUseCase {

    private final ICarRepository carRepository;

    @Transactional
    public void execute(UUID id, UpdateCarStatusDto dto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy xe với ID: " + id));

        CarAggregate carAggregate = CarAggregate.fromEntity(car);
        carAggregate.changeStatus(CarStatus.valueOf(dto.getStatus()));

        carRepository.save(carAggregate.getEntity());
    }
}
