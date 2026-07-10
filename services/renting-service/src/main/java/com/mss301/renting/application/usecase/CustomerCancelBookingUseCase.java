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
public class CustomerCancelBookingUseCase {

    private final IBookingRepository bookingRepository;
    private final CarServiceClient carServiceClient;
    private final BookingSagaLogJpaRepository sagaLogRepository;

    @Transactional
    public BookingDto execute(UUID userId, String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin đặt xe"));

        if (!booking.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Không tìm thấy thông tin đặt xe");
        }

        if (booking.getBookingStatus() != BookingStatus.INITIATED && booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Đơn đặt xe không ở trạng thái hợp lệ để hủy");
        }

        BookingStatus oldStatus = booking.getBookingStatus();
        PaymentStatus oldPaymentStatus = booking.getPaymentStatus();

        booking.setBookingStatus(BookingStatus.CANCELLED_DUE_TO_CUSTOMER);
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
            bookingRepository.save(savedBooking);
            throw new IllegalStateException("Có lỗi xảy ra khi hủy đơn, vui lòng thử lại sau.");
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
