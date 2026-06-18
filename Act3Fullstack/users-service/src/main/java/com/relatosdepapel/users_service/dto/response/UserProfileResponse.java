package com.relatosdepapel.users_service.dto.response;

import com.relatosdepapel.users_service.model.RoleEntity;
import com.relatosdepapel.users_service.model.UserEntity;

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
    public static UserProfileResponse from(UserEntity user) {
        List<String> roles = user.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .toList();

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEnabled(),
                roles
        );
    }
}
