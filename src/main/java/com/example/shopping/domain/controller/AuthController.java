package com.example.shopping.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.domain.dto.AuthDto;
import com.example.shopping.domain.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody @Valid AuthDto.SignupRequest request) {
        Long id = authService.signUp(request);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto.TokenResponse> postMethodName(@RequestBody @Valid AuthDto.LoginRequest request) {
        AuthDto.TokenResponse tr = authService.login(request);

        return ResponseEntity.ok(tr);
    }

}
