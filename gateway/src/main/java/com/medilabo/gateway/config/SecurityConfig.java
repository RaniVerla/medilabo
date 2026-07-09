package com.medilabo.gateway.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @PostConstruct
    public void init() {
        log.info("******** CUSTOM SECURITY CONFIG LOADED ********");
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        log.info("******** SECURITY FILTER CHAIN CREATED ********");

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(withDefaults())
                .authorizeExchange(exchange ->
                        exchange
                                .pathMatchers("/token")
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(withDefaults())
                )
                .build();
    }
}
