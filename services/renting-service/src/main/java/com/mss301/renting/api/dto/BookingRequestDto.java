package com.mss301.renting.api.dto;

import com.mss301.renting.domain.vo.PaymentMethod;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingRequestDto {

    @NotNull(message = "carId không được để trống")
    private UUID carId;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @Future(message = "Ngày bắt đầu phải ở tương lai")
    private LocalDateTime startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải ở tương lai")
    private LocalDateTime endDate;

    @NotBlank(message = "Địa điểm đón không được để trống")
    private String pickupLocation;

    @NotBlank(message = "Địa điểm trả không được để trống")
    private String dropoffLocation;

    @NotBlank(message = "Họ và tên không được để trống")
    private String renterName;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String renterPhone;

    @NotBlank(message = "Số CCCD không được để trống")
    private String renterCccd;

    @NotBlank(message = "Số GPLX không được để trống")
    private String renterLicense;

    private String customerNote;

    @NotNull(message = "Phương thức thanh toán không được để trống")
    private PaymentMethod paymentMethod;
}
