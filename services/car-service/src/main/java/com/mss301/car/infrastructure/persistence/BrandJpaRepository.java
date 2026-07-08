package com.mss301.car.infrastructure.persistence;
import com.mss301.car.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BrandJpaRepository extends JpaRepository<Brand, UUID> {
}
