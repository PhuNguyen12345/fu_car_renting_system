package com.mss301.renting.application.usecase;

import com.mss301.renting.domain.entity.Booking;
import com.mss301.renting.domain.repository.IBookingRepository;
import com.mss301.renting.domain.vo.PaymentMethod;
import com.mss301.renting.domain.vo.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.PaymentLinkData;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncPaymentStatusUseCase {

    private final IBookingRepository bookingRepository;
    private final PayOS payOS;

    public Booking execute(String bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn đặt xe: " + bookingId));

        if (booking.getPaymentStatus() == PaymentStatus.UNPAID && booking.getPaymentMethod() != PaymentMethod.CASH) {
            try {
                long orderCode = Long.parseLong(bookingId.replace("FPT-", ""));
                
                // Gọi sang PayOS để hỏi trạng thái
                PaymentLinkData paymentLinkData = payOS.getPaymentLinkInformation(orderCode);
                
                if (paymentLinkData != null && "PAID".equals(paymentLinkData.getStatus())) {
                    booking.setPaymentStatus(PaymentStatus.FULLY_PAID);
                    bookingRepository.save(booking);
                    log.info("Đã đồng bộ thành công! Đơn {} đã chuyển sang FULLY_PAID", bookingId);
                }
            } catch (Exception e) {
                log.warn("Không thể đồng bộ PayOS cho đơn {}: {}", bookingId, e.getMessage());
                throw new Exception("Lỗi khi kiểm tra trạng thái trên PayOS: " + e.getMessage());
            }
        }
        
        return booking;
    }
}
