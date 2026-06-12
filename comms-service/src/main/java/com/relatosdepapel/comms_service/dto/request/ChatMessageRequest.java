package com.relatosdepapel.comms_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(
        @NotBlank(message = "El mensaje es obligatorio")
        String message,

        String userId
) {
}
