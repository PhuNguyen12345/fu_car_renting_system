package com.mss301.customer.api.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
