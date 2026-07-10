package com.mss301.renting.infrastructure.adapter;

import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.infrastructure.persistence.BookingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookingRepositoryImpl implements IBookingRepository {

    private final BookingJpaRepository repository;

    @Override
    public Booking save(Booking booking) {
        return repository.save(booking);
    }

    @Override
    public Optional<Booking> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Page<Booking> findByUserId(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }

    @Override
    public Page<Booking> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
