package com.mss301.car.application.usecase;
import com.mss301.car.api.dto.LocationDto;
import com.mss301.car.domain.repository.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetLocationListUseCase {
    private final ILocationRepository repository;

    public List<LocationDto> execute() {
        return repository.findAll().stream()
                .map(LocationDto::fromEntity)
                .collect(Collectors.toList());
    }
}
