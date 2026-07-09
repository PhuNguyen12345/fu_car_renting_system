package com.mss301.customer.api.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;
    private java.time.LocalDate dateOfBirth;
}
