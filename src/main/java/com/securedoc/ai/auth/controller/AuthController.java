package com.securedoc.ai.auth.controller;

import com.securedoc.ai.auth.dto.AuthResponse;
import com.securedoc.ai.auth.dto.LoginRequest;
import com.securedoc.ai.auth.dto.RegisterRequest;
import com.securedoc.ai.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
        // return ResponseEntity with status 201 CREATED
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        // YOUR ATTEMPT
        // call authService.login(request)
        AuthResponse response=authService.login(request);
        // return ResponseEntity with status 200 OK
        return ResponseEntity.ok(response);

    }
}