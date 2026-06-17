package com.relatosdepapel.gateway.dto.response;

import java.util.List;

public record TokenValidationResponse(
        boolean valid,
        String accessToken,
        Long userId,
        String email,
        List<String> roles
) {
}
