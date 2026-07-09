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

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
        }

        if (dto.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Ngày sinh không được để trống");
        }
        
        if (java.time.Period.between(dto.getDateOfBirth(), java.time.LocalDate.now()).getYears() < 18) {
            throw new IllegalArgumentException("Bạn phải đủ 18 tuổi để sử dụng dịch vụ thuê xe");
        }

        Customer newCustomer = Customer.builder()
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .dateOfBirth(dto.getDateOfBirth())
                .role(CustomerRole.CUSTOMER) // Default role for new signups
                .status(CustomerStatus.ACTIVE)
                .build();

        customerRepository.save(newCustomer);
    }
}
