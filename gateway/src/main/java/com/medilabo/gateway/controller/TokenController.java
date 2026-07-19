package com.medilabo.gateway.controller;

import com.medilabo.gateway.config.JwtGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final JwtGenerator jwtGenerator;

    public TokenController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping("/token")
    public String token() {
        return jwtGenerator.generateToken("test-user");
    }
}