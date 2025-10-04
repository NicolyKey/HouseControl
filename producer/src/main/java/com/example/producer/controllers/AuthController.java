package com.example.producer.controllers;

import com.example.producer.controllers.dto.LoginRequest;
import com.example.producer.services.JwtService;
import com.example.producer.services.RabbitAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final RabbitAuthService rabbitAuthService;

    public AuthController(JwtService jwtService, RabbitAuthService rabbitAuthService) {
        this.jwtService = jwtService;
        this.rabbitAuthService = rabbitAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody  LoginRequest request) {
        if (rabbitAuthService.authenticate(request.getUsername(), request.getPassword())) {
            String token = jwtService.generateToken(request.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

