package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.LocationDto;
import com.mss301.car.api.dto.LocationRequestDto;
import com.mss301.car.domain.entity.Location;
import com.mss301.car.domain.repository.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateLocationUseCase {

    private final ILocationRepository locationRepository;

    @Transactional
    public LocationDto execute(LocationRequestDto request) {
        Location location = Location.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .build();

        Location savedLocation = locationRepository.save(location);
        return LocationDto.fromEntity(savedLocation);
    }
}
