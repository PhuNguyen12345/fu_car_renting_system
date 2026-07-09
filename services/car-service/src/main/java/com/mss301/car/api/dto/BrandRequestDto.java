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
public class BrandRequestDto {

    @NotBlank(message = "Tên hãng xe không được để trống")
    private String name;

    private String logoUrl;
}
