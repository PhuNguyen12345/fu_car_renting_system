package com.mss301.renting.application.usecase;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCustomerBookingDetailUseCase {

    private final IBookingRepository bookingRepository;

    public BookingDto execute(UUID userId, String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin đặt xe"));
        
        // Kiểm tra xem đơn này có đúng là của khách hàng đang đăng nhập không
        if (!booking.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Không tìm thấy thông tin đặt xe");
        }

        return BookingDto.fromEntity(booking);
    }
}
