package com.relatosdepapel.users_service.controller;

import com.relatosdepapel.users_service.model.RoleEntity;
import com.relatosdepapel.users_service.model.UserEntity;
import com.relatosdepapel.users_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @RequestHeader("accessToken") String jwtToken
    ) {
        UserEntity user = userService.getUserFromJwt(jwtToken);

        return ResponseEntity.ok(UserProfileResponse.from(user));
    }

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
}