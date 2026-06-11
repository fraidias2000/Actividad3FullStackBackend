package com.relatosdepapel.users_service.dto.response;

import com.relatosdepapel.users_service.service.AuthService;

import java.util.List;

public record TokenValidationResponse(
        boolean valid,
        String accessToken,
        Long userId,
        String email,
        List<String> roles
) {
    public static TokenValidationResponse from(AuthService.TokenValidationResult result) {
        return new TokenValidationResponse(
                result.valid(),
                result.jwtToken(),
                result.userId(),
                result.email(),
                result.roles()
        );
    }
}
