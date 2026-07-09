package com.mss301.customer.api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateCustomerDto {
    private String fullName;
    private String phone;
    private LocalDate dateOfBirth;
    private String cccd;
    private String cccdUrl;
    private String gplxNumber;
    private String gplxUrl;
    private String address;
    private String avatarUrl;
}
