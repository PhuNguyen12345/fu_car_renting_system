package com.mss301.car.infrastructure.adapter;
import com.mss301.car.domain.entity.Location;
import com.mss301.car.domain.repository.ILocationRepository;
import com.mss301.car.infrastructure.persistence.LocationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LocationRepositoryImpl implements ILocationRepository {
    private final LocationJpaRepository repository;

    @Override
    public List<Location> findAll() { 
        return repository.findAll(); 
    }

    @Override
    public Optional<Location> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Location save(Location location) {
        return repository.save(location);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
