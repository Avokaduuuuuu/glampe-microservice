package com.avocado.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // FIX: use OR here to avoid NPE
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        return Mono.fromCallable(() -> {
                    if (!jwtProvider.isTokenValid(token)) {
                        return null;
                    }
                    String subject = jwtProvider.parseSubject(token);
                    List<String> roles = jwtProvider.parseClaims(token, claims -> claims.get("roles", List.class));
                    var authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
                    return new UsernamePasswordAuthenticationToken(subject, null, authorities);
                })
                .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic()) // safe for blocking calls
                .flatMap(auth -> {
                    if (auth == null) {
                        return chain.filter(exchange);
                    }
                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(new SecurityContextImpl(auth))));
                });
    }
}
