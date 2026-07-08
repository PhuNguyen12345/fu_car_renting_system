package com.mss301.car.api.dto;
import com.mss301.car.domain.entity.Brand;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class BrandDto {
    private UUID id;
    private String name;
    private String logoUrl;

    public static BrandDto fromEntity(Brand brand) {
        return BrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .logoUrl(brand.getLogoUrl())
                .build();
    }
}
