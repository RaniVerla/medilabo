package com.medilabo.gateway.config;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {

    private final SecretKey secretKey;


    public JwtGenerator(SecretKey secretKey) {
        this.secretKey = secretKey;
    }


    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 60 * 60 * 1000
                        )
                )
                .signWith(secretKey)
                .compact();
    }
}