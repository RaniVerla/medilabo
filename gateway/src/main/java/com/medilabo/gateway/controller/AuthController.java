package com.medilabo.gateway.controller;

import com.medilabo.gateway.config.JwtGenerator;
import com.medilabo.gateway.dto.LoginRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final ReactiveAuthenticationManager authenticationManager;

    private final JwtGenerator jwtGenerator;


    @PostMapping("/login")
    public  Mono<ResponseEntity<?>>  login(@RequestBody LoginRequest loginRequest)
    {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                );


        return authenticationManager.authenticate(authentication)

                .map(auth -> {
                    String token =
                            jwtGenerator.generateToken(
                                    loginRequest.getUsername()
                            );
                    return ResponseEntity.ok(
                            Map.of(
                                    "token",
                                    token
                            )
                    );

                });
    }

}
