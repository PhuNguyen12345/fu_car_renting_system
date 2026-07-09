package com.mss301.car.application.usecase;

import com.mss301.car.domain.entity.Location;
import com.mss301.car.domain.repository.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteLocationUseCase {

    private final ILocationRepository locationRepository;

    @Transactional
    public void execute(UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chi nhánh với ID: " + id));

        try {
            locationRepository.deleteById(location.getId());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Không thể xóa chi nhánh này vì đã có xe được gán vào đây.");
        }
    }
}
