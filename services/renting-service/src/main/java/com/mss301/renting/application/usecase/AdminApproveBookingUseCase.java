package com.mss301.renting.application.usecase;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.domain.vo.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminApproveBookingUseCase {

    private final IBookingRepository bookingRepository;

    @Transactional
    public BookingDto execute(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin đặt xe"));

        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Chỉ được duyệt đơn khi trạng thái đang là CONFIRMED (Khách đã cọc)");
        }

        booking.setBookingStatus(BookingStatus.APPROVED);
        return BookingDto.fromEntity(bookingRepository.save(booking));
    }
}
