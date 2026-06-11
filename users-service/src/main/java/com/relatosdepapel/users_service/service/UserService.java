package com.relatosdepapel.users_service.service;

import com.relatosdepapel.users_service.model.RoleEntity;
import com.relatosdepapel.users_service.model.UserEntity;
import com.relatosdepapel.users_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public UserEntity getUserFromJwt(String jwtToken) {
        String email = jwtService.extractEmail(jwtToken);
        return findByEmail(email);
    }

    public List<String> getUserRoles(UserEntity user) {
        return user.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .toList();
    }
}
