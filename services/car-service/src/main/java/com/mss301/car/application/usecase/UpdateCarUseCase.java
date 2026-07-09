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

import java.util.List;
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

        Brand brand = car.getBrand();
        if (dto.getBrandId() != null) {
            brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hãng xe"));
        }
        
        Location location = car.getLocation();
        if (dto.getLocationId() != null) {
            location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy địa điểm"));
        }

        CarAggregate carAggregate = CarAggregate.fromEntity(car);
        
        CarStatus status = null;
        if(dto.getStatus() != null) {
            status = CarStatus.valueOf(dto.getStatus());
        }

        carAggregate.updateDetails(
            dto.getName() != null ? dto.getName() : car.getName(), 
            brand, 
            location, 
            dto.getType() != null ? dto.getType() : car.getType(),
            dto.getPricePerDay() != null ? dto.getPricePerDay() : car.getPricePerDay(), 
            dto.getSeats() != null ? dto.getSeats() : car.getSeats(), 
            dto.getTransmission() != null ? dto.getTransmission() : car.getTransmission(),
            dto.getFuelType() != null ? dto.getFuelType() : car.getFuelType(), 
            dto.getFuelConsumption() != null ? dto.getFuelConsumption() : car.getFuelConsumption(), 
            dto.getLicensePlate() != null ? dto.getLicensePlate() : car.getLicensePlate(),
            dto.getDescription() != null ? dto.getDescription() : car.getDescription(), 
            status
        );

        if (dto.getFeatures() != null) {
            carAggregate.setFeatures(dto.getFeatures());
        }
        
        if (dto.getThumbnailUrl() != null || dto.getGalleryUrls() != null) {
            // we only update images if new ones are provided. If one is null, we should probably keep existing or handle properly. 
            // Since images is a collection, let's look at what the UI usually does. 
            // In partial update, they might just not send thumbnailUrl if it didn't change.
            // Let's just call updateImages if either is provided, but use existing if null.
            String thumb = dto.getThumbnailUrl() != null ? dto.getThumbnailUrl() : 
                car.getImages().stream().filter(i -> Boolean.TRUE.equals(i.getIsPrimary())).findFirst().map(i -> i.getImageUrl()).orElse(null);
            
            List<String> gallery = dto.getGalleryUrls() != null ? dto.getGalleryUrls() :
                car.getImages().stream().filter(i -> Boolean.FALSE.equals(i.getIsPrimary())).map(i -> i.getImageUrl()).toList();
                
            carAggregate.updateImages(thumb, gallery);
        }

        carRepository.save(carAggregate.getEntity());
    }
}
