package com.relatosdepapel.users_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class OpaqueTokenService {

    private static final String TOKEN_PREFIX = "opaque-token:";

    private final StringRedisTemplate redisTemplate;

    @Value("${app.opaque-token.expiration-ms}")
    private long opaqueTokenExpirationMs;

    public OpaqueTokenService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //AQUI SE GUARDA LA RELACIÓN ENTRE EL TOKEN OPACO Y EL JWT EN REDIS
    public String createOpaqueToken(String jwtToken) {
        String opaqueToken = UUID.randomUUID().toString();
        String redisKey = buildRedisKey(opaqueToken);

        redisTemplate.opsForValue().set(
                redisKey,
                jwtToken,
                Duration.ofMillis(opaqueTokenExpirationMs)
        );

        return opaqueToken;
    }

    public Optional<String> findJwtByOpaqueToken(String opaqueToken) {
        String redisKey = buildRedisKey(opaqueToken);
        String jwtToken = redisTemplate.opsForValue().get(redisKey);

        return Optional.ofNullable(jwtToken);
    }

    public void deleteOpaqueToken(String opaqueToken) {
        redisTemplate.delete(buildRedisKey(opaqueToken));
    }

    private String buildRedisKey(String opaqueToken) {
        return TOKEN_PREFIX + opaqueToken;
    }
}
