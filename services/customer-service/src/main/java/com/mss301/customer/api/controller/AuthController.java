package com.mss301.customer.api.controller;

import com.mss301.customer.api.dto.AuthResponseDto;
import com.mss301.customer.api.dto.LoginRequestDto;
import com.mss301.customer.api.dto.RegisterRequestDto;
import com.mss301.customer.application.usecase.LoginUseCase;
import com.mss301.customer.application.usecase.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDto dto) {
        registerUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(loginUseCase.execute(dto));
    }
}
