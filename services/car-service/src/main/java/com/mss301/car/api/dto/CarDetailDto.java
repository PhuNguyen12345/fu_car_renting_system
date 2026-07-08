package com.mss301.car.api.dto;

import com.mss301.car.domain.entity.Car;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class CarDetailDto {
    private UUID id;
    private String name;
    private String brandName;
    private String brandLogo;
    private String type;
    private BigDecimal pricePerDay;
    private Integer seats;
    private String transmission;
    private String fuelType;
    private String fuelConsumption;
    private String licensePlate;
    private String status;
    private String description;
    private String locationName;
    private String city;
    private String address;
    private List<String> features;
    private List<ImageDto> images;

    @Data
    @Builder
    public static class ImageDto {
        private String url;
        private Boolean isPrimary;
    }

    public static CarDetailDto fromEntity(Car car) {
        return CarDetailDto.builder()
                .id(car.getId())
                .name(car.getName())
                .brandName(car.getBrand() != null ? car.getBrand().getName() : null)
                .brandLogo(car.getBrand() != null ? car.getBrand().getLogoUrl() : null)
                .type(car.getType())
                .pricePerDay(car.getPricePerDay())
                .seats(car.getSeats())
                .transmission(car.getTransmission())
                .fuelType(car.getFuelType())
                .fuelConsumption(car.getFuelConsumption())
                .licensePlate(car.getLicensePlate())
                .status(car.getStatus().name())
                .description(car.getDescription())
                .locationName(car.getLocation() != null ? car.getLocation().getName() : null)
                .city(car.getLocation() != null ? car.getLocation().getCity() : null)
                .address(car.getLocation() != null ? car.getLocation().getAddress() : null)
                .features(car.getFeatures())
                .images(car.getImages().stream()
                        .map(img -> ImageDto.builder()
                                .url(img.getImageUrl())
                                .isPrimary(img.getIsPrimary())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
