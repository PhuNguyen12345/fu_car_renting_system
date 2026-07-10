package com.mss301.renting.application.usecase;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.domain.repository.IBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMyRentalsUseCase {

    private final IBookingRepository bookingRepository;

    public Page<BookingDto> execute(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return bookingRepository.findByUserId(userId, pageable)
                .map(BookingDto::fromEntity);
    }
}
