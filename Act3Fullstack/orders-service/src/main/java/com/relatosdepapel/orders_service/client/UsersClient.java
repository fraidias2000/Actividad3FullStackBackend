package com.relatosdepapel.orders_service.client;

import com.relatosdepapel.orders_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "users-service")
public interface UsersClient {

    //EN BASE A UN JWT DEVUELVE TODOS LOS DATOS DE UN USUARIO
    @GetMapping("/api/users/me")
    UserProfileResponse getMyProfile(@RequestHeader("accessToken") String accessToken);
}
