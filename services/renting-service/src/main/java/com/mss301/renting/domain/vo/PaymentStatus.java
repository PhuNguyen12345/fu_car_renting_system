package com.mss301.renting.domain.vo;

public enum PaymentStatus {
    UNPAID, // Chưa thanh toán
    PARTIALLY_PAID, // Đã thanh toán cọc
    FULLY_PAID, // Đã thanh toán toàn bộ
    REFUNDED // Đã hoàn tiền (nếu hủy đơn)
}
