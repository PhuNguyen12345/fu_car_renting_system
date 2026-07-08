package com.mss301.car.infrastructure.adapter;
import com.mss301.car.domain.entity.Brand;
import com.mss301.car.domain.repository.IBrandRepository;
import com.mss301.car.infrastructure.persistence.BrandJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BrandRepositoryImpl implements IBrandRepository {
    private final BrandJpaRepository repository;

    @Override
    public List<Brand> findAll() { 
        return repository.findAll(); 
    }

    @Override
    public Optional<Brand> findById(UUID id) {
        return repository.findById(id);
    }
}
