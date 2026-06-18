package com.relatosdepapel.comms_service.dto.response;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        String userMessage,
        String agentResponse,
        LocalDateTime timestamp
) {
}
