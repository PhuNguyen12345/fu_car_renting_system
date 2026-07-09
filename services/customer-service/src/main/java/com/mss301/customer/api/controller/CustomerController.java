package com.mss301.customer.api.controller;

import com.mss301.customer.api.dto.CloudinarySignatureDto;
import com.mss301.customer.api.dto.CustomerDto;
import com.mss301.customer.api.dto.UpdateCustomerDto;
import com.mss301.customer.application.usecase.GenerateCloudinarySignatureUseCase;
import com.mss301.customer.application.usecase.GetCustomerProfileUseCase;
import com.mss301.customer.application.usecase.UpdateCustomerProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final GetCustomerProfileUseCase getCustomerProfileUseCase;
    private final UpdateCustomerProfileUseCase updateCustomerProfileUseCase;
    private final GenerateCloudinarySignatureUseCase generateCloudinarySignatureUseCase;

    @GetMapping("/me")
    public ResponseEntity<CustomerDto> getMyProfile(Authentication authentication) {
        String email = authentication.getName(); // Lấy email từ token (username)
        return ResponseEntity.ok(getCustomerProfileUseCase.execute(email));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMyProfile(Authentication authentication, @RequestBody UpdateCustomerDto dto) {
        String email = authentication.getName();
        updateCustomerProfileUseCase.execute(email, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cloudinary-signature")
    public ResponseEntity<CloudinarySignatureDto> getCloudinarySignature() {
        return ResponseEntity.ok(generateCloudinarySignatureUseCase.execute());
    }
}
