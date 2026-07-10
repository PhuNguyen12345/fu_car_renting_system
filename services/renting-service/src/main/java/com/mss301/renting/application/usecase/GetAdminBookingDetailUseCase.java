package com.mss301.renting.application.usecase;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAdminBookingDetailUseCase {

    private final IBookingRepository bookingRepository;

    public BookingDto execute(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin đặt xe"));

        return BookingDto.fromEntity(booking);
    }
}
