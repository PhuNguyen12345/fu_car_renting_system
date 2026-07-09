package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.LocationDto;
import com.mss301.car.api.dto.LocationRequestDto;
import com.mss301.car.domain.entity.Location;
import com.mss301.car.domain.repository.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateLocationUseCase {

    private final ILocationRepository locationRepository;

    @Transactional
    public LocationDto execute(UUID id, LocationRequestDto request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chi nhánh với ID: " + id));

        location.setName(request.getName());
        location.setCity(request.getCity());
        location.setAddress(request.getAddress());

        Location updatedLocation = locationRepository.save(location);
        return LocationDto.fromEntity(updatedLocation);
    }
}
