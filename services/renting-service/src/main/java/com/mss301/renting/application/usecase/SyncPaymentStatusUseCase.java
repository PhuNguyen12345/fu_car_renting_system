package com.mss301.renting.application.usecase;

import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.domain.vo.PaymentMethod;
import com.mss301.renting.domain.vo.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncPaymentStatusUseCase {

    private final IBookingRepository bookingRepository;

    @Value("${payos.client-id}")
    private String clientId;

    @Value("${payos.api-key}")
    private String apiKey;

    public Booking execute(String bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn đặt xe: " + bookingId));

        if (booking.getPaymentStatus() == PaymentStatus.UNPAID && booking.getPaymentMethod() != PaymentMethod.CASH) {
            try {
                long orderCode = Long.parseLong(bookingId.replace("FPT-", ""));

                // Gọi HTTP GET thẳng sang PayOS để lấy thông tin đơn hàng
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("x-client-id", clientId);
                headers.set("x-api-key", apiKey);

                HttpEntity<Void> entity = new HttpEntity<>(headers);
                String url = "https://api-merchant.payos.vn/v2/payment-requests/" + orderCode;

                Map<String, Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class).getBody();

                if (response != null && "00".equals(response.get("code"))) {
                    Map<String, Object> data = (Map<String, Object>) response.get("data");
                    if (data != null && "PAID".equals(data.get("status"))) {
                        booking.setPaymentStatus(PaymentStatus.FULLY_PAID);
                        bookingRepository.save(booking);
                        log.info("Đã đồng bộ thành công! Đơn {} đã chuyển sang FULLY_PAID", bookingId);
                    }
                }
            } catch (Exception e) {
                log.warn("Không thể đồng bộ PayOS cho đơn {}: {}", bookingId, e.getMessage());
                throw new Exception("Lỗi khi kiểm tra trạng thái trên PayOS: " + e.getMessage());
            }
        }

        return booking;
    }
}
