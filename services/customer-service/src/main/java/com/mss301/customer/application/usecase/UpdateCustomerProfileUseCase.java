package com.mss301.customer.application.usecase;

import com.mss301.customer.api.dto.UpdateCustomerDto;
import com.mss301.customer.domain.entity.Customer;
import com.mss301.customer.domain.repository.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCustomerProfileUseCase {

    private final ICustomerRepository customerRepository;

    @Transactional
    public void execute(String email, UpdateCustomerDto dto) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thông tin khách hàng"));

        if (dto.getFullName() != null) customer.setFullName(dto.getFullName());
        if (dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if (dto.getDateOfBirth() != null) customer.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getCccd() != null) customer.setCccd(dto.getCccd());
        if (dto.getCccdUrl() != null) customer.setCccdUrl(dto.getCccdUrl());
        if (dto.getGplxNumber() != null) customer.setGplxNumber(dto.getGplxNumber());
        if (dto.getGplxUrl() != null) customer.setGplxUrl(dto.getGplxUrl());
        if (dto.getAddress() != null) customer.setAddress(dto.getAddress());
        if (dto.getAvatarUrl() != null) customer.setAvatarUrl(dto.getAvatarUrl());

        customerRepository.save(customer);
    }
}
