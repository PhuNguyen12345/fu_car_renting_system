package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.BrandDto;
import com.mss301.car.api.dto.UpdateBrandRequestDto;
import com.mss301.car.domain.entity.Brand;
import com.mss301.car.domain.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateBrandUseCase {

    private final IBrandRepository brandRepository;

    @Transactional
    public BrandDto execute(UUID id, UpdateBrandRequestDto request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hãng xe với ID: " + id));

        if (StringUtils.hasText(request.getName())) {
            brand.setName(request.getName());
        }
        if (request.getLogoUrl() != null) {
            brand.setLogoUrl(request.getLogoUrl());
        }

        Brand updatedBrand = brandRepository.save(brand);
        return BrandDto.fromEntity(updatedBrand);
    }
}
