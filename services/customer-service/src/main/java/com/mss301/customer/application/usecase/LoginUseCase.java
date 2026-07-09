package com.mss301.customer.application.usecase;

import com.mss301.customer.api.dto.AuthResponseDto;
import com.mss301.customer.api.dto.LoginRequestDto;
import com.mss301.customer.domain.entity.Customer;
import com.mss301.customer.domain.repository.ICustomerRepository;
import com.mss301.customer.domain.vo.CustomerStatus;
import com.mss301.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final ICustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponseDto execute(LoginRequestDto dto) {
        Customer customer = customerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email hoặc mật khẩu không chính xác"));

        if (!passwordEncoder.matches(dto.getPassword(), customer.getPasswordHash())) {
            throw new IllegalArgumentException("Email hoặc mật khẩu không chính xác");
        }

        if (customer.getStatus() == CustomerStatus.LOCKED) {
            throw new IllegalArgumentException("Tài khoản của bạn đã bị khóa");
        }

        // Generate JWT Token
        String token = jwtUtils.generateToken(customer.getEmail(), customer.getRole().name(), customer.getId().toString());

        return AuthResponseDto.builder()
                .token(token)
                .customerId(customer.getId())
                .email(customer.getEmail())
                .role(customer.getRole().name())
                .build();
    }
}
