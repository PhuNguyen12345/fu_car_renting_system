package com.mss301.customer.api.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String cccd;
}
