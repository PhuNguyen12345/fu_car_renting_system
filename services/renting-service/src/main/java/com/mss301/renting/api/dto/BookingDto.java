package com.mss301.renting.api.dto;

import com.mss301.renting.domain.entity.Booking;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BookingDto {
    private String id;
    private UUID userId;
    private UUID carId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pickupLocation;
    private String dropoffLocation;
    private BigDecimal totalAmount;
    private BigDecimal depositAmount;
    private String paymentMethod;
    private String bookingStatus;
    private String paymentStatus;
    private String renterName;
    private String renterPhone;
    private String renterCccd;
    private String renterLicense;
    private String customerNote;
    private String adminNote;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BookingDto fromEntity(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .carId(booking.getCarId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .pickupLocation(booking.getPickupLocation())
                .dropoffLocation(booking.getDropoffLocation())
                .totalAmount(booking.getTotalAmount())
                .depositAmount(booking.getDepositAmount())
                .paymentMethod(booking.getPaymentMethod().name())
                .bookingStatus(booking.getBookingStatus().name())
                .paymentStatus(booking.getPaymentStatus().name())
                .renterName(booking.getRenterName())
                .renterPhone(booking.getRenterPhone())
                .renterCccd(booking.getRenterCccd())
                .renterLicense(booking.getRenterLicense())
                .customerNote(booking.getCustomerNote())
                .adminNote(booking.getAdminNote())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}
