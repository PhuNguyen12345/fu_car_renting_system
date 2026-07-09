package com.mss301.car.application.usecase;

import com.mss301.car.domain.entity.Brand;
import com.mss301.car.domain.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBrandUseCase {

    private final IBrandRepository brandRepository;

    @Transactional
    public void execute(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hãng xe với ID: " + id));

        try {
            brandRepository.deleteById(brand.getId());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Không thể xóa hãng xe này vì đã có xe thuộc hãng này.");
        }
    }
}
