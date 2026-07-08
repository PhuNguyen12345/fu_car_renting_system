package com.mss301.car.domain.repository;

import com.mss301.car.domain.entity.Car;
import com.mss301.car.api.dto.CarSearchCriteria;
import com.mss301.car.api.dto.AdminCarSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICarRepository {
    Optional<Car> findById(UUID id);
    List<Car> findAll();
    Page<Car> search(CarSearchCriteria criteria, Pageable pageable);
    Page<Car> searchAdmin(AdminCarSearchCriteria criteria, Pageable pageable);
    Car save(Car car);
    void deleteById(UUID id);
}
