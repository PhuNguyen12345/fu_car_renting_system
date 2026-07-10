package com.mss301.renting.application.usecase;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.api.dto.BookingRequestDto;
import com.mss301.renting.api.dto.CarDetailDto;
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

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBookingUseCase {

    private final IBookingRepository bookingRepository;
    private final BookingSagaLogJpaRepository sagaLogRepository;
    private final CarServiceClient carServiceClient;
    private static final SecureRandom random = new SecureRandom();

    @Transactional
    public BookingDto execute(UUID customerId, BookingRequestDto request) {
        // 1. Lấy thông tin giá xe từ car-service
        CarDetailDto carDetail;
        try {
            carDetail = carServiceClient.getCarDetail(request.getCarId());
        } catch (Exception e) {
            log.error("Lỗi khi gọi car-service lấy thông tin xe: {}", e.getMessage());
            throw new IllegalArgumentException("Không thể lấy thông tin xe hoặc xe không tồn tại");
        }

        // 2. Tính toán tổng số ngày và số tiền
        long days = ChronoUnit.DAYS.between(request.getStartDate().toLocalDate(), request.getEndDate().toLocalDate());
        if (days <= 0) {
            days = 1; // Ít nhất 1 ngày
        }

        BigDecimal pricePerDay = carDetail.getPricePerDay();
        BigDecimal totalAmount = pricePerDay.multiply(BigDecimal.valueOf(days));
        
        // Cọc mặc định là 30% tổng tiền
        BigDecimal depositAmount = totalAmount.multiply(new BigDecimal("0.30"));

        // 3. Khởi tạo mã Booking (Format: FPT-XXXXXX)
        String bookingId = generateBookingId();

        // 4. Khởi tạo Entity Booking
        Booking booking = Booking.builder()
                .id(bookingId)
                .userId(customerId)
                .carId(request.getCarId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .pickupLocation(request.getPickupLocation())
                .dropoffLocation(request.getDropoffLocation())
                .totalAmount(totalAmount)
                .depositAmount(depositAmount)
                .paymentMethod(request.getPaymentMethod())
                .bookingStatus(BookingStatus.INITIATED)
                .paymentStatus(PaymentStatus.UNPAID)
                .renterName(request.getRenterName())
                .renterPhone(request.getRenterPhone())
                .renterCccd(request.getRenterCccd())
                .renterLicense(request.getRenterLicense())
                .customerNote(request.getCustomerNote())
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        // 5. Ghi log Saga bắt đầu
        saveSagaLog(bookingId, "LOCK_CAR", "STARTED", null);

        // 6. Thực thi Orchestration Saga (Gọi sang car-service để khóa xe)
        try {
            carServiceClient.lockCar(request.getCarId());
            saveSagaLog(bookingId, "LOCK_CAR", "COMPLETED", null);
        } catch (Exception e) {
            log.error("Saga Error - Lock Car failed for Booking ID: {}. Error: {}", bookingId, e.getMessage());
            saveSagaLog(bookingId, "LOCK_CAR", "FAILED", e.getMessage());
            // Rollback trạng thái Booking
            savedBooking.setBookingStatus(BookingStatus.CANCELLED_DUE_TO_CAR);
            bookingRepository.save(savedBooking);
            throw new IllegalStateException("Rất tiếc, xe này vừa được người khác đặt hoặc đang không khả dụng.");
        }

        return BookingDto.fromEntity(savedBooking);
    }

    private String generateBookingId() {
        int randomNumber = 100000 + random.nextInt(900000); // Sinh số từ 100000 đến 999999
        return "FPT-" + randomNumber;
    }

    private void saveSagaLog(String bookingId, String step, String status, String error) {
        BookingSagaLog log = BookingSagaLog.builder()
                .bookingId(bookingId)
                .currentStep(step)
                .sagaStatus(status)
                .errorMessage(error)
                .build();
        sagaLogRepository.save(log);
    }
}
