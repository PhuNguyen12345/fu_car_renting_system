package com.mss301.customer.application.usecase;

import com.mss301.customer.api.dto.CustomerDto;
import com.mss301.customer.domain.entity.Customer;
import com.mss301.customer.infrastructure.persistence.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAdminCustomerListUseCase {

    private final CustomerJpaRepository jpaRepository;

    public List<CustomerDto> execute() {
        List<Customer> customers = jpaRepository.findAll();
        
        return customers.stream().map(customer -> CustomerDto.builder()
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
                .build()).collect(Collectors.toList());
    }
}
