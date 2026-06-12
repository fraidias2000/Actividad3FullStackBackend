package com.relatosdepapel.users_service.service;

import com.relatosdepapel.users_service.dto.result.LoginResult;
import com.relatosdepapel.users_service.dto.result.TokenValidationResult;
import com.relatosdepapel.users_service.model.RefreshTokenEntity;
import com.relatosdepapel.users_service.model.UserEntity;
import com.relatosdepapel.users_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OpaqueTokenService opaqueTokenService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            OpaqueTokenService opaqueTokenService,
            RefreshTokenService refreshTokenService,
            UserService userService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.opaqueTokenService = opaqueTokenService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    public LoginResult login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new RuntimeException("Usuario deshabilitado");
        }

        if (!Boolean.TRUE.equals(user.getAccountNonLocked())) {
            throw new RuntimeException("Usuario bloqueado");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String jwtToken = jwtService.generateToken(user);
        String opaqueToken = opaqueTokenService.createOpaqueToken(jwtToken);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user);

        List<String> roles = userService.getUserRoles(user);

        return new LoginResult(
                opaqueToken,
                refreshToken.getToken(),
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                roles
        );
    }

    public TokenValidationResult validateOpaqueToken(String opaqueToken) {
        //Busca el JWT con el token opaco
        String jwtToken = opaqueTokenService.findJwtByOpaqueToken(opaqueToken)
                .orElseThrow(() -> new RuntimeException("Token opaco inválido"));

        if (!jwtService.isTokenValid(jwtToken)) {
            opaqueTokenService.deleteOpaqueToken(opaqueToken);
            throw new RuntimeException("JWT expirado o inválido");
        }

        //Obtiene los claims
        String email = jwtService.extractEmail(jwtToken);
        Long userId = jwtService.extractUserId(jwtToken);
        List<String> roles = jwtService.extractRoles(jwtToken);

        return new TokenValidationResult(
                true,
                jwtToken,
                userId,
                email,
                roles
        );
    }

    //Valida el refresh token, obtiene el usuario y genera un nuevo JWT y otro token opaco
    public LoginResult refresh(String refreshTokenValue) {
        RefreshTokenEntity refreshToken = refreshTokenService.validateRefreshToken(refreshTokenValue);

        UserEntity user = refreshToken.getUser();

        String jwtToken = jwtService.generateToken(user);
        String opaqueToken = opaqueTokenService.createOpaqueToken(jwtToken);

        List<String> roles = userService.getUserRoles(user);

        return new LoginResult(
                opaqueToken,
                refreshToken.getToken(),
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                roles
        );
    }




}