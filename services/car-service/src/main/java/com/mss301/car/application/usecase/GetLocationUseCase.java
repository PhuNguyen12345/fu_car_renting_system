package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.LocationDto;
import com.mss301.car.domain.entity.Location;
import com.mss301.car.domain.repository.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetLocationUseCase {

    private final ILocationRepository locationRepository;

    public LocationDto execute(UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chi nhánh với ID: " + id));
        return LocationDto.fromEntity(location);
    }
}
