package com.example.lab.spring.reactive.web.spring.config.security.service;

import com.example.lab.spring.reactive.web.spring.config.security.dto.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class TokenUserService {

    @Value("#{T(io.jsonwebtoken.security.Keys).hmacShaKeyFor(T(io.jsonwebtoken.io.Decoders).BASE64.decode('${jwt.secret-key}'))}")
    private Key key;

    public AuthenticatedUser toUser(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return AuthenticatedUser.builder()
                .userId(claims.get("userId", Integer.class))
                .userName(claims.get("username", String.class))
                .build();
    }
}
