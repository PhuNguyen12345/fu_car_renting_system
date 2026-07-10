package com.mss301.renting.api.controller;

import com.mss301.renting.api.dto.BookingDto;
import com.mss301.renting.api.dto.BookingRequestDto;
import com.mss301.renting.application.usecase.CreateBookingUseCase;
import com.mss301.renting.application.usecase.GetCustomerBookingDetailUseCase;
import com.mss301.renting.application.usecase.GetMyRentalsUseCase;
import com.mss301.renting.application.usecase.MockPaymentUseCase;
import com.mss301.renting.application.usecase.CustomerCancelBookingUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class BookingCustomerController {

    private final CreateBookingUseCase createBookingUseCase;
    private final GetMyRentalsUseCase getMyRentalsUseCase;
    private final GetCustomerBookingDetailUseCase getCustomerBookingDetailUseCase;
    private final MockPaymentUseCase mockPaymentUseCase;
    private final CustomerCancelBookingUseCase customerCancelBookingUseCase;
    private final com.mss301.security.JwtUtils jwtUtils;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingRequestDto request, jakarta.servlet.http.HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        String userIdStr = jwtUtils.getClaimsFromJwtToken(token).get("id", String.class);
        UUID userId = UUID.fromString(userIdStr);
        return ResponseEntity.ok(createBookingUseCase.execute(userId, request));
    }

    @GetMapping("/my-rentals")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Page<BookingDto>> getMyRentals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            jakarta.servlet.http.HttpServletRequest httpRequest) {
        
        String token = httpRequest.getHeader("Authorization").substring(7);
        String userIdStr = jwtUtils.getClaimsFromJwtToken(token).get("id", String.class);
        UUID userId = UUID.fromString(userIdStr);
        
        return ResponseEntity.ok(getMyRentalsUseCase.execute(userId, page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingDto> getBookingDetail(
            @PathVariable("id") String bookingId,
            jakarta.servlet.http.HttpServletRequest httpRequest) {
        
        String token = httpRequest.getHeader("Authorization").substring(7);
        String userIdStr = jwtUtils.getClaimsFromJwtToken(token).get("id", String.class);
        UUID userId = UUID.fromString(userIdStr);
        
        return ResponseEntity.ok(getCustomerBookingDetailUseCase.execute(userId, bookingId));
    }

    @PatchMapping("/{id}/pay-deposit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingDto> mockPaymentDeposit(
            @PathVariable("id") String bookingId,
            jakarta.servlet.http.HttpServletRequest httpRequest) {
        
        String token = httpRequest.getHeader("Authorization").substring(7);
        String userIdStr = jwtUtils.getClaimsFromJwtToken(token).get("id", String.class);
        UUID userId = UUID.fromString(userIdStr);
        
        return ResponseEntity.ok(mockPaymentUseCase.execute(userId, bookingId, false));
    }

    @PatchMapping("/{id}/pay-full")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingDto> mockPaymentFull(
            @PathVariable("id") String bookingId,
            jakarta.servlet.http.HttpServletRequest httpRequest) {
        
        String token = httpRequest.getHeader("Authorization").substring(7);
        String userIdStr = jwtUtils.getClaimsFromJwtToken(token).get("id", String.class);
        UUID userId = UUID.fromString(userIdStr);
        
        return ResponseEntity.ok(mockPaymentUseCase.execute(userId, bookingId, true));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingDto> cancelBooking(
            @PathVariable("id") String bookingId,
            jakarta.servlet.http.HttpServletRequest httpRequest) {
        
        String token = httpRequest.getHeader("Authorization").substring(7);
        String userIdStr = jwtUtils.getClaimsFromJwtToken(token).get("id", String.class);
        UUID userId = UUID.fromString(userIdStr);
        
        return ResponseEntity.ok(customerCancelBookingUseCase.execute(userId, bookingId));
    }
}
