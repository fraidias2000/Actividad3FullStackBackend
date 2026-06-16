package com.relatosdepapel.orders_service.dto.response;

import java.util.List;

public record UserProfileResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        Boolean enabled,
        List<String> roles
) {
}