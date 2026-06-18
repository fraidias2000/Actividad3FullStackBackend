package com.relatosdepapel.users_service.controller;

import com.relatosdepapel.users_service.dto.response.UserProfileResponse;
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

    //EN BASE A UN JWT DEVUELVE TODOS LOS DATOS DE UN USUARIO
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @RequestHeader("accessToken") String jwtToken
    ) {
        UserEntity user = userService.getUserFromJwt(jwtToken);

        return ResponseEntity.ok(UserProfileResponse.from(user));
    }


}