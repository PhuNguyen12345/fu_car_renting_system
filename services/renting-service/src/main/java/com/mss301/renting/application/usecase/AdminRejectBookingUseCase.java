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

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminRejectBookingUseCase {

    private final IBookingRepository bookingRepository;
    private final CarServiceClient carServiceClient;
    private final BookingSagaLogJpaRepository sagaLogRepository;

    @Transactional
    public BookingDto execute(String bookingId, String adminNote) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin đặt xe"));

        if (booking.getBookingStatus() == BookingStatus.ACTIVE || booking.getBookingStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Đơn này không thể từ chối vì xe đang được sử dụng hoặc đã hoàn thành.");
        }

        BookingStatus oldStatus = booking.getBookingStatus();
        PaymentStatus oldPaymentStatus = booking.getPaymentStatus();

        booking.setBookingStatus(BookingStatus.CANCELLED_DUE_TO_CAR);
        booking.setAdminNote(adminNote);
        if (booking.getPaymentStatus() == PaymentStatus.PARTIALLY_PAID || booking.getPaymentStatus() == PaymentStatus.FULLY_PAID) {
            booking.setPaymentStatus(PaymentStatus.REFUNDED);
        }
        Booking savedBooking = bookingRepository.save(booking);

        saveSagaLog(bookingId, "UNLOCK_CAR", "STARTED", null);
        try {
            carServiceClient.unlockCar(booking.getCarId());
            saveSagaLog(bookingId, "UNLOCK_CAR", "COMPLETED", null);
        } catch (Exception e) {
            log.error("Saga Error - Unlock Car failed for Booking ID: {}. Error: {}", bookingId, e.getMessage());
            saveSagaLog(bookingId, "UNLOCK_CAR", "FAILED", e.getMessage());
            // Rollback trạng thái
            savedBooking.setBookingStatus(oldStatus);
            savedBooking.setPaymentStatus(oldPaymentStatus);
            savedBooking.setAdminNote(null);
            bookingRepository.save(savedBooking);
            throw new IllegalStateException("Có lỗi xảy ra khi hủy đơn và nhả khóa xe.");
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
