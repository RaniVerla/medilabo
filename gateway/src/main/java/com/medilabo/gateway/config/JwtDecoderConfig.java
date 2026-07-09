package com.medilabo.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import javax.crypto.SecretKey;

@Configuration
public class JwtDecoderConfig {

    @Bean
    public ReactiveJwtDecoder jwtDecoder(SecretKey secretKey) {

        return NimbusReactiveJwtDecoder
                .withSecretKey(secretKey)
                .build();
    }
}