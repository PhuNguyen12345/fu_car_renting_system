package com.mss301.car.api.dto;

import lombok.Data;

@Data
public class AdminCarSearchCriteria {
    private String keyword; // Tìm theo name hoặc licensePlate
    private String status; // Tìm theo trạng thái
}
