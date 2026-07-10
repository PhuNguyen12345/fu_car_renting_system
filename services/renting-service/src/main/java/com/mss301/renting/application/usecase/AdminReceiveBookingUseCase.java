package com.mss301.renting.application.usecase;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.entity.BookingSagaLog;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.domain.vo.BookingStatus;
import com.mss301.renting.infrastructure.adapter.CarServiceClient;
import com.mss301.renting.infrastructure.persistence.BookingSagaLogJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminReceiveBookingUseCase {

    private final IBookingRepository bookingRepository;
    private final CarServiceClient carServiceClient;
    private final BookingSagaLogJpaRepository sagaLogRepository;

    @Transactional
    public BookingDto execute(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin đặt xe"));

        if (booking.getBookingStatus() != BookingStatus.ACTIVE) {
            throw new IllegalStateException("Chỉ được nhận lại xe khi trạng thái đang là ACTIVE");
        }

        BookingStatus oldStatus = booking.getBookingStatus();
        booking.setBookingStatus(BookingStatus.COMPLETED);
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
            bookingRepository.save(savedBooking);
            throw new IllegalStateException("Có lỗi xảy ra khi cập nhật trạng thái nhận xe.");
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
