package com.mss301.car.domain.repository;
import com.mss301.car.domain.entity.Location;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILocationRepository {
    List<Location> findAll();
    Optional<Location> findById(UUID id);
}
