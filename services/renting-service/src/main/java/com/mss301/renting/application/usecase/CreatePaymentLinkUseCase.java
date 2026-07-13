package com.mss301.renting.application.usecase;

import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.domain.vo.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;

@Service
@RequiredArgsConstructor
public class CreatePaymentLinkUseCase {

    private final IBookingRepository bookingRepository;
    private final PayOS payOS;

    public String execute(String bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn đặt xe"));

        long orderCode;
        try {
            orderCode = Long.parseLong(bookingId.replace("FPT-", ""));
        } catch (NumberFormatException e) {
            // Fallback just in case
            orderCode = System.currentTimeMillis() % 1000000;
        }

        int amount = 0;
        if (booking.getPaymentMethod() == PaymentMethod.CASH) {
            amount = booking.getDepositAmount().intValue();
        } else {
            amount = booking.getTotalAmount().intValue();
        }

        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCode)
                .amount(amount)
                .description("Don hang dat xe FUCar " + bookingId)
                .returnUrl("http://localhost:3000/user-dashboard?tab=bookings")
                .cancelUrl("http://localhost:3000/user-dashboard?tab=bookings")
                .build();

        CheckoutResponseData data = payOS.createPaymentLink(paymentData);
        return data.getCheckoutUrl();
    }
}
