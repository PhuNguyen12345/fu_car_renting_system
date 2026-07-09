package com.mss301.customer.api.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {
    private UUID id;
    private String email;
    private String fullName;
    private String phone;
    private LocalDate dateOfBirth;
    private String cccd;
    private String cccdUrl;
    private String gplxNumber;
    private String address;
    private String avatarUrl;
    private String gplxUrl;
    private String status;
    private String role;
}
