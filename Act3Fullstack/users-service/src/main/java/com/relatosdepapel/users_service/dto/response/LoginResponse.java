package com.relatosdepapel.users_service.dto.response;

import com.relatosdepapel.users_service.dto.result.LoginResult;
import com.relatosdepapel.users_service.service.AuthService;

import java.util.List;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Long userId,
        String email,
        String firstName,
        String lastName,
        List<String> roles
) {
    public static LoginResponse from(LoginResult result) {
        return new LoginResponse(
                result.accessToken(),
                result.refreshToken(),
                result.userId(),
                result.email(),
                result.firstName(),
                result.lastName(),
                result.roles()
        );
    }
}
