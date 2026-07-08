package com.mss301.car.infrastructure.persistence;

import com.mss301.car.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarJpaRepository extends JpaRepository<Car, UUID>, JpaSpecificationExecutor<Car> {
    // Có thể định nghĩa thêm các Spring Data JPA queries ở đây (VD: findByStatus)
}
