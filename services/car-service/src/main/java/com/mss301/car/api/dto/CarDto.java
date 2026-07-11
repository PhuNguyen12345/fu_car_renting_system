package com.mss301.car.api.dto;

import com.mss301.car.domain.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private UUID id;
    private String name;
    private UUID brandId;
    private String brandName;
    private String type;
    private BigDecimal pricePerDay;
    private Integer seats;
    private String transmission;
    private String fuelType;
    private String status;
    private String licensePlate;

    private String imageUrl;
    private UUID locationId;
    private String locationName;
    private String city;

    public static CarDto fromEntity(Car car) {
        // Tìm ảnh primary, nếu không có thì null
        String primaryImage = car.getImages().stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsPrimary()))
                .map(img -> img.getImageUrl())
                .findFirst()
                .orElse(null);

        return CarDto.builder()
                .id(car.getId())
                .name(car.getName())
                .brandId(car.getBrand() != null ? car.getBrand().getId() : null)
                .brandName(car.getBrand() != null ? car.getBrand().getName() : null)
                .type(car.getType())
                .pricePerDay(car.getPricePerDay())
                .seats(car.getSeats())
                .transmission(car.getTransmission())
                .fuelType(car.getFuelType())
                .status(car.getStatus().name())
                .licensePlate(car.getLicensePlate())
                .imageUrl(primaryImage)
                .locationId(car.getLocation() != null ? car.getLocation().getId() : null)
                .locationName(car.getLocation() != null ? car.getLocation().getName() : null)
                .city(car.getLocation() != null ? car.getLocation().getCity() : null)
                .build();
    }
}
