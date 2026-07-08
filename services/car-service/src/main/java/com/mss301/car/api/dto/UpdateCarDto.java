package com.mss301.car.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateCarDto {
    private String name;
    private UUID brandId;
    private UUID locationId;
    private String type;
    private BigDecimal pricePerDay;
    private Integer seats;
    private String transmission;
    private String fuelType;
    private String fuelConsumption;
    private String licensePlate;
    private String description;
    private String status; // Tùy chọn, vì Update form có dropdown Trạng thái
    
    private List<String> features;
    private String thumbnailUrl;
    private List<String> galleryUrls;
}
