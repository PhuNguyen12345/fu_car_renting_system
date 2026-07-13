package com.mss301.renting.api.controller;

import com.mss301.renting.application.usecase.CreatePaymentLinkUseCase;
import com.mss301.renting.application.usecase.HandlePayOSWebhookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final CreatePaymentLinkUseCase createPaymentLinkUseCase;
    private final HandlePayOSWebhookUseCase handlePayOSWebhookUseCase;
    private final com.mss301.renting.application.usecase.SyncPaymentStatusUseCase syncPaymentStatusUseCase;

    @PostMapping("/create-link")
    public ResponseEntity<Map<String, String>> createPaymentLink(@RequestBody Map<String, String> payload) {
        try {
            String bookingId = payload.get("bookingId");
            String checkoutUrl = createPaymentLinkUseCase.execute(bookingId);
            
            Map<String, String> response = new HashMap<>();
            response.put("checkoutUrl", checkoutUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(err);
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<Map<String, Object>> handleWebhook(@RequestBody Object webhookBody) {
        try {
            handlePayOSWebhookUseCase.execute(webhookBody);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/sync/{bookingId}")
    public ResponseEntity<?> syncPayment(@PathVariable String bookingId) {
        try {
            com.mss301.renting.domain.entity.Booking updatedBooking = syncPaymentStatusUseCase.execute(bookingId);
            return ResponseEntity.ok(updatedBooking);
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(err);
        }
    }
}
