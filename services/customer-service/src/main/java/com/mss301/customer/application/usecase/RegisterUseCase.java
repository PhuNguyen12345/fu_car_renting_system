package com.mss301.customer.application.usecase;

import com.mss301.customer.api.dto.RegisterRequestDto;
import com.mss301.customer.domain.entity.Customer;
import com.mss301.customer.domain.repository.ICustomerRepository;
import com.mss301.customer.domain.vo.CustomerRole;
import com.mss301.customer.domain.vo.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final ICustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(RegisterRequestDto dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại trong hệ thống");
        }

        Customer newCustomer = Customer.builder()
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .cccd(dto.getCccd())
                .role(CustomerRole.CUSTOMER) // Default role for new signups
                .status(CustomerStatus.ACTIVE)
                .build();

        customerRepository.save(newCustomer);
    }
}
