package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.AdminCarSearchCriteria;
import com.mss301.car.api.dto.CarDto;
import com.mss301.car.domain.repository.ICarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAdminCarListUseCase {
    private final ICarRepository carRepository;

    public Page<CarDto> execute(AdminCarSearchCriteria criteria, Pageable pageable) {
        return carRepository.searchAdmin(criteria, pageable).map(CarDto::fromEntity);
    }
}
