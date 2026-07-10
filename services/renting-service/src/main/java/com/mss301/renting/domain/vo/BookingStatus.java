package com.mss301.renting.domain.vo;

public enum BookingStatus {
    INITIATED, // Vừa tạo đơn, chờ thanh toán cọc
    CONFIRMED, // Đã thanh toán cọc, đã khóa xe bên car-service
    APPROVED, // Admin duyệt đơn, chuẩn bị giao xe
    ACTIVE, // Khách đã lấy xe và đang dùng
    COMPLETED, // Khách đã trả xe, thanh toán xong xuôi
    CANCELLED_DUE_TO_CAR, // Hủy do lỗi từ phía xe (Hư hỏng đột xuất)
    CANCELLED_DUE_TO_CUSTOMER, // Khách tự hủy
    CANCELLED // Bị hủy do hệ thống (Ví dụ Timeout quá 15p không cọc)
}
