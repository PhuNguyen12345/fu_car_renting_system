package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.BrandDto;
import com.mss301.car.domain.entity.Brand;
import com.mss301.car.domain.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBrandUseCase {

    private final IBrandRepository brandRepository;

    public BrandDto execute(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hãng xe với ID: " + id));
        return BrandDto.fromEntity(brand);
    }
}
