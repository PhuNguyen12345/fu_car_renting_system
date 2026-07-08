package com.mss301.car.application.usecase;
import com.mss301.car.api.dto.BrandDto;
import com.mss301.car.domain.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetBrandListUseCase {
    private final IBrandRepository repository;

    public List<BrandDto> execute() {
        return repository.findAll().stream()
                .map(BrandDto::fromEntity)
                .collect(Collectors.toList());
    }
}
