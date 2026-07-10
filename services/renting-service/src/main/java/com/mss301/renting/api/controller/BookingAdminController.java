package com.mss301.renting.api.controller;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.application.usecase.AdminApproveBookingUseCase;
import com.mss301.renting.application.usecase.AdminHandoverBookingUseCase;
import com.mss301.renting.application.usecase.AdminReceiveBookingUseCase;
import com.mss301.renting.application.usecase.AdminRejectBookingUseCase;
import com.mss301.renting.application.usecase.GetAdminBookingDetailUseCase;
import com.mss301.renting.application.usecase.GetAllRentalsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/rentals")
@RequiredArgsConstructor
public class BookingAdminController {

    private final GetAllRentalsUseCase getAllRentalsUseCase;
    private final GetAdminBookingDetailUseCase getAdminBookingDetailUseCase;
    private final AdminApproveBookingUseCase adminApproveBookingUseCase;
    private final AdminRejectBookingUseCase adminRejectBookingUseCase;
    private final AdminHandoverBookingUseCase adminHandoverBookingUseCase;
    private final AdminReceiveBookingUseCase adminReceiveBookingUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookingDto>> getAllRentals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(getAllRentalsUseCase.execute(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> getBookingDetail(@PathVariable("id") String bookingId) {
        return ResponseEntity.ok(getAdminBookingDetailUseCase.execute(bookingId));
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> approveBooking(@PathVariable("id") String bookingId) {
        return ResponseEntity.ok(adminApproveBookingUseCase.execute(bookingId));
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> rejectBooking(
            @PathVariable("id") String bookingId,
            @RequestBody java.util.Map<String, String> body) {
        String adminNote = body.get("adminNote");
        return ResponseEntity.ok(adminRejectBookingUseCase.execute(bookingId, adminNote));
    }

    @PatchMapping("/{id}/handover")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> handoverBooking(@PathVariable("id") String bookingId) {
        return ResponseEntity.ok(adminHandoverBookingUseCase.execute(bookingId));
    }

    @PatchMapping("/{id}/receive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> receiveBooking(@PathVariable("id") String bookingId) {
        return ResponseEntity.ok(adminReceiveBookingUseCase.execute(bookingId));
    }
}
