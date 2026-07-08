package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.UpdateCarDto;
import com.mss301.car.domain.aggregate.CarAggregate;
import com.mss301.car.domain.entity.Brand;
import com.mss301.car.domain.entity.Car;
import com.mss301.car.domain.entity.Location;
import com.mss301.car.domain.repository.IBrandRepository;
import com.mss301.car.domain.repository.ICarRepository;
import com.mss301.car.domain.repository.ILocationRepository;
import com.mss301.car.domain.vo.CarStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCarUseCase {

    private final ICarRepository carRepository;
    private final IBrandRepository brandRepository;
    private final ILocationRepository locationRepository;

    @Transactional
    public void execute(UUID id, UpdateCarDto dto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy xe với ID: " + id));

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hãng xe"));
        
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy địa điểm"));

        CarAggregate carAggregate = CarAggregate.fromEntity(car);
        
        CarStatus status = null;
        if(dto.getStatus() != null) {
            status = CarStatus.valueOf(dto.getStatus());
        }

        carAggregate.updateDetails(
            dto.getName(), brand, location, dto.getType(),
            dto.getPricePerDay(), dto.getSeats(), dto.getTransmission(),
            dto.getFuelType(), dto.getFuelConsumption(), dto.getLicensePlate(),
            dto.getDescription(), status
        );

        carAggregate.setFeatures(dto.getFeatures());
        carAggregate.updateImages(dto.getThumbnailUrl(), dto.getGalleryUrls());

        carRepository.save(carAggregate.getEntity());
    }
}
