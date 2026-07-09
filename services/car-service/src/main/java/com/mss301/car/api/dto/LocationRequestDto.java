package com.mss301.car.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDto {

    @NotBlank(message = "Tên chi nhánh không được để trống")
    private String name;

    @NotBlank(message = "Thành phố/Tỉnh không được để trống")
    private String city;

    @NotBlank(message = "Địa chỉ chi tiết không được để trống")
    private String address;
}
