package com.relatosdepapel.users_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenValidationRequest(
        @NotBlank(message = "El accessToken es obligatorio")
        String accessToken
) {
}
