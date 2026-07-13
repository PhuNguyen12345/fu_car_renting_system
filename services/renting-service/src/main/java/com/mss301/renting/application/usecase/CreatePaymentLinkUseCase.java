package com.mss301.renting.application.usecase;

import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.domain.vo.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreatePaymentLinkUseCase {

    private final IBookingRepository bookingRepository;

    @Value("${payos.client-id}")
    private String clientId;

    @Value("${payos.api-key}")
    private String apiKey;

    @Value("${payos.checksum-key}")
    private String checksumKey;

    public String execute(String bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn đặt xe"));

        long orderCode;
        try {
            orderCode = Long.parseLong(bookingId.replace("FPT-", ""));
        } catch (NumberFormatException e) {
            orderCode = System.currentTimeMillis() % 1000000;
        }

        int amount = booking.getPaymentMethod() == PaymentMethod.CASH ?
                booking.getDepositAmount().intValue() : booking.getTotalAmount().intValue();

        String cancelUrl = "http://localhost:3000/user-dashboard?tab=bookings";
        String returnUrl = "http://localhost:3000/user-dashboard?tab=bookings";
        String description = "Thanh toan " + bookingId;

        // 1. Tạo chữ ký (Signature) bằng HmacSHA256 để bypass lỗi của thư viện SDK 1.0.3
        String dataToHash = "amount=" + amount + "&cancelUrl=" + cancelUrl + "&description=" + description + "&orderCode=" + orderCode + "&returnUrl=" + returnUrl;
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(new SecretKeySpec(checksumKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] hashBytes = hmacSha256.doFinal(dataToHash.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        String signature = hexString.toString();

        // 2. Gọi HTTP API thẳng đến PayOS bằng RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-client-id", clientId);
        headers.set("x-api-key", apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("orderCode", orderCode);
        body.put("amount", amount);
        body.put("description", description);
        body.put("cancelUrl", cancelUrl);
        body.put("returnUrl", returnUrl);
        body.put("signature", signature);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        String url = "https://api-merchant.payos.vn/v2/payment-requests";

        Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
        if (response != null && "00".equals(response.get("code"))) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            return (String) data.get("checkoutUrl");
        }

        throw new RuntimeException("Lỗi tạo link PayOS: " + response);
    }
}
