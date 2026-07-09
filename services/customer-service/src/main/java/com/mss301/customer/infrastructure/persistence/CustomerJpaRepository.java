package com.mss301.customer.infrastructure.persistence;

import com.mss301.customer.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, UUID> {
    Customer findByEmail(String email);
    boolean existsByEmail(String email);
}
