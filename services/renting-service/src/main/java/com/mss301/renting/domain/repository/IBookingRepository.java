package com.mss301.renting.domain.repository;

import com.mss301.renting.domain.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IBookingRepository {
    Booking save(Booking booking);
    Optional<Booking> findById(String id);
    Page<Booking> findByUserId(UUID userId, Pageable pageable);
    Page<Booking> findAll(Pageable pageable);
}
