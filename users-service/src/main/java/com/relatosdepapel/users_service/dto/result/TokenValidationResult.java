package com.relatosdepapel.users_service.dto.result;

import java.util.List;

public record TokenValidationResult(
        boolean valid,
        String jwtToken,
        Long userId,
        String email,
        List<String> roles
) {
}
