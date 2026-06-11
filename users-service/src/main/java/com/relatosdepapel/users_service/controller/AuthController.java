package com.relatosdepapel.users_service.controller;

import com.relatosdepapel.users_service.dto.request.LoginRequest;
import com.relatosdepapel.users_service.dto.request.RefreshTokenRequest;
import com.relatosdepapel.users_service.dto.request.TokenValidationRequest;
import com.relatosdepapel.users_service.dto.response.LoginResponse;
import com.relatosdepapel.users_service.dto.response.TokenValidationResponse;
import com.relatosdepapel.users_service.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthService.LoginResult result = authService.login(
                request.email(),
                request.password()
        );

        return ResponseEntity.ok(LoginResponse.from(result));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(
            @Valid @RequestBody TokenValidationRequest request
    ) {
        AuthService.TokenValidationResult result = authService.validateOpaqueToken(
                request.accessToken()
        );

        return ResponseEntity.ok(TokenValidationResponse.from(result));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthService.LoginResult result = authService.refresh(
                request.refreshToken()
        );

        return ResponseEntity.ok(LoginResponse.from(result));
    }





}