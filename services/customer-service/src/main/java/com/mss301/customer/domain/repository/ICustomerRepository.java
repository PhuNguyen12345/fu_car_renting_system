package com.mss301.customer.domain.repository;

import com.mss301.customer.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface ICustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
}
