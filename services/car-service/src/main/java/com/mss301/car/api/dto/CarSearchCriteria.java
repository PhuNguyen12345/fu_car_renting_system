package com.mss301.car.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CarSearchCriteria {
    private String keyword;
    private String city;
    private String type;
    private String transmission;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
