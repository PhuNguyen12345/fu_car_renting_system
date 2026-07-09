package com.mss301.customer.application.usecase;

import com.mss301.customer.domain.entity.Customer;
import com.mss301.customer.domain.repository.ICustomerRepository;
import com.mss301.customer.domain.vo.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BanCustomerUseCase {

    private final ICustomerRepository customerRepository;

    @Transactional
    public void execute(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + customerId));

        if (customer.getStatus() == CustomerStatus.LOCKED) {
            customer.setStatus(CustomerStatus.ACTIVE);
            customer.setAdminNote(null);
        } else {
            customer.setStatus(CustomerStatus.LOCKED);
            customer.setAdminNote("Bị khóa bởi Admin");
        }

        customerRepository.save(customer);
    }
}
