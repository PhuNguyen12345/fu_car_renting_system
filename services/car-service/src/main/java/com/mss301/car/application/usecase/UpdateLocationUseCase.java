package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.LocationDto;
import com.mss301.car.api.dto.UpdateLocationRequestDto;
import com.mss301.car.domain.entity.Location;
import com.mss301.car.domain.repository.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateLocationUseCase {

    private final ILocationRepository locationRepository;

    @Transactional
    public LocationDto execute(UUID id, UpdateLocationRequestDto request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chi nhánh với ID: " + id));

        if (StringUtils.hasText(request.getName())) {
            location.setName(request.getName());
        }
        if (StringUtils.hasText(request.getCity())) {
            location.setCity(request.getCity());
        }
        if (StringUtils.hasText(request.getAddress())) {
            location.setAddress(request.getAddress());
        }

        Location updatedLocation = locationRepository.save(location);
        return LocationDto.fromEntity(updatedLocation);
    }
}
