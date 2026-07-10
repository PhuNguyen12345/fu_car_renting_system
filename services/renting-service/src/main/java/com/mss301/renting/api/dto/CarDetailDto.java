package com.mss301.renting.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CarDetailDto {
    private UUID id;
    private String name;
    private BigDecimal pricePerDay;
}
