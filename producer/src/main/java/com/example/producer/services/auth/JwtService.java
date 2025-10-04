package com.example.producer.services.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final String secret = "Y9YvR8M2czP3EZF5TgAMG6uQZzY9j+WdUSYHnF3m1fqG5T6hL7Qb8ZbYxV7rJ6oM5P3bG9rF2mT8sK9vL0dE4uT7rH6xJ5oL1";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String validate(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}

