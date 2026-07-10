package com.mss301.renting.infrastructure.persistence;

import com.mss301.renting.domain.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingJpaRepository extends JpaRepository<Booking, String> {
    Page<Booking> findByUserId(UUID userId, Pageable pageable);
}
