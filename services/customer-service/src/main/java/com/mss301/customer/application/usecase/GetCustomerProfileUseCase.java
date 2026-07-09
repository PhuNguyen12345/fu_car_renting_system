package com.mss301.customer.application.usecase;

import com.mss301.customer.api.dto.CustomerDto;
import com.mss301.customer.domain.entity.Customer;
import com.mss301.customer.domain.repository.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCustomerProfileUseCase {

    private final ICustomerRepository customerRepository;

    public CustomerDto execute(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin khách hàng"));

        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .dateOfBirth(customer.getDateOfBirth())
                .cccd(customer.getCccd())
                .cccdUrl(customer.getCccdUrl())
                .gplxNumber(customer.getGplxNumber())
                .gplxUrl(customer.getGplxUrl())
                .address(customer.getAddress())
                .avatarUrl(customer.getAvatarUrl())
                .status(customer.getStatus().name())
                .role(customer.getRole().name())
                .build();
    }
}
