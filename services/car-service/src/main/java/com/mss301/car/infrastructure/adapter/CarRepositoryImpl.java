package com.mss301.car.infrastructure.adapter;

import com.mss301.car.domain.entity.Car;
import com.mss301.car.domain.repository.ICarRepository;
import com.mss301.car.infrastructure.persistence.CarJpaRepository;
import com.mss301.car.api.dto.CarSearchCriteria;
import com.mss301.car.api.dto.AdminCarSearchCriteria;
import com.mss301.car.infrastructure.specification.CarSpecification;
import com.mss301.car.infrastructure.specification.AdminCarSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CarRepositoryImpl implements ICarRepository {

    private final CarJpaRepository carJpaRepository;

    @Override
    public Optional<Car> findById(UUID id) {
        return carJpaRepository.findById(id);
    }

    @Override
    public List<Car> findAll() {
        return carJpaRepository.findAll();
    }

    @Override
    public Page<Car> search(CarSearchCriteria criteria, Pageable pageable) {
        return carJpaRepository.findAll(CarSpecification.buildQuery(criteria), pageable);
    }

    @Override
    public Page<Car> searchAdmin(AdminCarSearchCriteria criteria, Pageable pageable) {
        return carJpaRepository.findAll(AdminCarSpecification.buildQuery(criteria), pageable);
    }

    @Override
    public Car save(Car car) {
        return carJpaRepository.save(car);
    }

    @Override
    public void deleteById(UUID id) {
        carJpaRepository.deleteById(id);
    }
}
