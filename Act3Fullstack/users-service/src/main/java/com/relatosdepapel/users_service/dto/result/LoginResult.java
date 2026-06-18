package com.relatosdepapel.users_service.dto.result;

import java.util.List;

public record LoginResult(
        String accessToken,
        String refreshToken,
        Long userId,
        String email,
        String firstName,
        String lastName,
        List<String> roles
) {
}
