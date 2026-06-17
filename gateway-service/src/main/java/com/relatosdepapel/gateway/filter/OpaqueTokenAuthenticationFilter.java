package com.relatosdepapel.gateway.filter;

import com.relatosdepapel.gateway.dto.request.TokenValidationRequest;
import com.relatosdepapel.gateway.dto.response.TokenValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpaqueTokenAuthenticationFilter implements GlobalFilter, Ordered {

    private final WebClient.Builder webClientBuilder;

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }
        String path = exchange.getRequest().getURI().getPath();

        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS || !requiresAuthentication(path)) {
            return chain.filter(exchange);
        }

        String opaqueToken = exchange.getRequest().getHeaders().getFirst("accessToken");

        if (opaqueToken == null || opaqueToken.isBlank()) {
            log.warn("Petición protegida sin token opaco: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return webClientBuilder.build()
                .post()
                .uri("http://users-service/api/auth/validate")
                .bodyValue(new TokenValidationRequest(opaqueToken))
                .retrieve()
                .bodyToMono(TokenValidationResponse.class)
                .flatMap(validationResponse -> {
                    if (validationResponse == null
                            || !validationResponse.valid()
                            || validationResponse.accessToken() == null
                            || validationResponse.accessToken().isBlank()) {

                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }

                    String jwtToken = validationResponse.accessToken();

                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .headers(headers -> {
                                headers.remove("accessToken");
                                headers.set("accessToken", jwtToken);

                                if (validationResponse.userId() != null) {
                                    headers.set("X-User-Id", String.valueOf(validationResponse.userId()));
                                }

                                if (validationResponse.email() != null) {
                                    headers.set("X-User-Email", validationResponse.email());
                                }
                            })
                            .build();

                    log.info("Token opaco validado. Enviando JWT interno a {}", path);

                    return chain.filter(
                            exchange.mutate()
                                    .request(mutatedRequest)
                                    .build()
                    );
                })
                .onErrorResume(exception -> {
                    log.warn("Error validando token opaco: {}", exception.getMessage());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    private boolean requiresAuthentication(String path) {
        return path.contains("/orders-service/api/orders")
                || path.contains("/users-service/api/users/me");
    }
}
