package com.mss301.renting.application.usecase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.domain.vo.PaymentMethod;
import com.mss301.renting.domain.vo.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePayOSWebhookUseCase {

    private final IBookingRepository bookingRepository;
    private final PayOS payOS;
    private final ObjectMapper objectMapper;

    public void execute(Object webhookBody) throws Exception {
        // Convert the generic webhook object to string
        String jsonString = objectMapper.writeValueAsString(webhookBody);
        JsonNode rootNode = objectMapper.readTree(jsonString);

        // Use ObjectMapper to bypass package-private constructor and deserialize the JSON directly
        Webhook webhook = objectMapper.readValue(jsonString, Webhook.class);

        // Verify the signature
        WebhookData data = payOS.verifyPaymentWebhookData(webhook);
        
        if (data != null) {
            long orderCode = data.getOrderCode();
            String bookingId = "FPT-" + orderCode;
            log.info("Received successful webhook for bookingId: {}", bookingId);

            Booking booking = bookingRepository.findById(bookingId)
                    .orElse(null);

            if (booking != null && booking.getPaymentStatus() == PaymentStatus.UNPAID) {
                if (booking.getPaymentMethod() == PaymentMethod.CASH) {
                    booking.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
                } else {
                    booking.setPaymentStatus(PaymentStatus.FULLY_PAID);
                }
                bookingRepository.save(booking);
                log.info("Updated PaymentStatus for bookingId: {}", bookingId);
            }
        }
    }
}
