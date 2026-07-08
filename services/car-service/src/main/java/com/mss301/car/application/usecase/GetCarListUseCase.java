package com.mss301.car.application.usecase;

import com.mss301.car.api.dto.CarDto;
import com.mss301.car.domain.repository.ICarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.mss301.car.api.dto.CarSearchCriteria;


@Service
@RequiredArgsConstructor
public class GetCarListUseCase {
    
    // Inject Interface của Domain, không inject JpaRepository!
    private final ICarRepository carRepository;

    public Page<CarDto> execute(CarSearchCriteria criteria, Pageable pageable) {
        return carRepository.search(criteria, pageable)
                .map(CarDto::fromEntity);
    }
}
