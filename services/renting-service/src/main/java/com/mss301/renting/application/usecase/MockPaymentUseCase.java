package com.mss301.renting.application.usecase;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.entity.BookingSagaLog;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.domain.vo.BookingStatus;
import com.mss301.renting.domain.vo.PaymentStatus;
import com.mss301.renting.infrastructure.adapter.CarServiceClient;
import com.mss301.renting.infrastructure.persistence.BookingSagaLogJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MockPaymentUseCase {

    private final IBookingRepository bookingRepository;
    private final CarServiceClient carServiceClient;
    private final BookingSagaLogJpaRepository sagaLogRepository;

    @Transactional
    public BookingDto execute(UUID userId, String bookingId, boolean isFullPayment) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin đặt xe"));

        if (!booking.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Không tìm thấy thông tin đặt xe");
        }

        if (booking.getBookingStatus() != BookingStatus.INITIATED) {
            throw new IllegalStateException("Đơn đặt xe không ở trạng thái hợp lệ để thanh toán");
        }

        // Cập nhật trạng thái
        booking.setPaymentStatus(isFullPayment ? PaymentStatus.FULLY_PAID : PaymentStatus.PARTIALLY_PAID);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        Booking savedBooking = bookingRepository.save(booking);

        // Saga
        saveSagaLog(bookingId, "CONFIRM_RENTED", "STARTED", null);
        try {
            carServiceClient.confirmRentedCar(booking.getCarId());
            saveSagaLog(bookingId, "CONFIRM_RENTED", "COMPLETED", null);
        } catch (Exception e) {
            log.error("Saga Error - Confirm Rented failed for Booking ID: {}. Error: {}", bookingId, e.getMessage());
            saveSagaLog(bookingId, "CONFIRM_RENTED", "FAILED", e.getMessage());
            // Rollback trạng thái
            savedBooking.setPaymentStatus(PaymentStatus.UNPAID);
            savedBooking.setBookingStatus(BookingStatus.INITIATED);
            bookingRepository.save(savedBooking);
            throw new IllegalStateException("Có lỗi xảy ra khi xác nhận xe, vui lòng thử lại sau.");
        }

        return BookingDto.fromEntity(savedBooking);
    }

    private void saveSagaLog(String bookingId, String step, String status, String error) {
        BookingSagaLog sagaLog = BookingSagaLog.builder()
                .bookingId(bookingId)
                .currentStep(step)
                .sagaStatus(status)
                .errorMessage(error)
                .build();
        sagaLogRepository.save(sagaLog);
    }
}
