package com.mss301.renting.domain.vo;

public enum PaymentMethod {
    TRANSFER, // Chuyển khoản ngân hàng (PayOS/VietQR)
    CREDIT_CARD, // Thẻ tín dụng
    MOMO, // Ví điện tử MoMo
    VNPAY, // VNPay
    CASH // Tiền mặt (Áp dụng thanh toán phần còn lại)
}
