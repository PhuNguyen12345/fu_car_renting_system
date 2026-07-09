package com.mss301.customer.api.controller;

import com.mss301.customer.api.dto.CustomerDto;
import com.mss301.customer.application.usecase.GetInternalCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/internal/customers")
@RequiredArgsConstructor
public class CustomerInternalController {

    private final GetInternalCustomerUseCase getInternalCustomerUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID id) {
        return ResponseEntity.ok(getInternalCustomerUseCase.execute(id));
    }
}
