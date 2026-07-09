package com.mss301.customer.api.controller;

import com.mss301.customer.api.dto.CustomerDto;
import com.mss301.customer.application.usecase.BanCustomerUseCase;
import com.mss301.customer.application.usecase.GetAdminCustomerListUseCase;
import com.mss301.customer.application.usecase.RestoreCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/customers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CustomerAdminController {

    private final GetAdminCustomerListUseCase getAdminCustomerListUseCase;
    private final BanCustomerUseCase banCustomerUseCase;
    private final RestoreCustomerUseCase restoreCustomerUseCase;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(getAdminCustomerListUseCase.execute());
    }

    @PatchMapping("/{id}/ban")
    public ResponseEntity<Void> banCustomer(@PathVariable UUID id) {
        banCustomerUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restoreCustomer(@PathVariable UUID id) {
        restoreCustomerUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
