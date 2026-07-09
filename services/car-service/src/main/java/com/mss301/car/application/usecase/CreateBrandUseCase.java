package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.BrandDto;
import com.mss301.car.api.dto.BrandRequestDto;
import com.mss301.car.domain.entity.Brand;
import com.mss301.car.domain.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBrandUseCase {

    private final IBrandRepository brandRepository;

    @Transactional
    public BrandDto execute(BrandRequestDto request) {
        Brand brand = Brand.builder()
                .name(request.getName())
                .logoUrl(request.getLogoUrl())
                .build();

        Brand savedBrand = brandRepository.save(brand);
        return BrandDto.fromEntity(savedBrand);
    }
}
