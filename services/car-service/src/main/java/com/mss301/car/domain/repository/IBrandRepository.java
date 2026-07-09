package com.mss301.car.domain.repository;
import com.mss301.car.domain.entity.Brand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBrandRepository {
    List<Brand> findAll();
    Optional<Brand> findById(UUID id);
    Brand save(Brand brand);
    void deleteById(UUID id);
}
