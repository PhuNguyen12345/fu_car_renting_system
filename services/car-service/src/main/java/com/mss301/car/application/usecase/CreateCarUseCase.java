package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.CreateCarDto;
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

@Service
@RequiredArgsConstructor
public class CreateCarUseCase {

    private final ICarRepository carRepository;
    private final IBrandRepository brandRepository;
    private final ILocationRepository locationRepository;

    @Transactional
    public void execute(CreateCarDto dto) {
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hãng xe"));
        
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy địa điểm"));

        // Dùng Builder để tạo Entity
        Car newCar = Car.builder()
                .name(dto.getName())
                .brand(brand)
                .location(location)
                .type(dto.getType())
                .pricePerDay(dto.getPricePerDay())
                .seats(dto.getSeats())
                .transmission(dto.getTransmission())
                .fuelType(dto.getFuelType())
                .fuelConsumption(dto.getFuelConsumption())
                .licensePlate(dto.getLicensePlate())
                .description(dto.getDescription())
                .status(CarStatus.AVAILABLE) // Khởi tạo mặc định là AVAILABLE
                .build();
                
        // Áp dụng Aggregate Pattern để gắn tính năng / hình ảnh
        CarAggregate carAggregate = CarAggregate.fromEntity(newCar);
        carAggregate.setFeatures(dto.getFeatures());
        carAggregate.updateImages(dto.getThumbnailUrl(), dto.getGalleryUrls());

        carRepository.save(carAggregate.getEntity());
    }
}
