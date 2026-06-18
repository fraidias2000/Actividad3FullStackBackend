package com.relatosdepapel.orders_service.dto.event;

import java.util.Map;

public record EmailEvent(
        String to,
        String subject,
        String body,
        Map<String, Object> metadata
) {
}
