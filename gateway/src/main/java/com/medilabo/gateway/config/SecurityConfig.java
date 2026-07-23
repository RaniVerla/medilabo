package com.medilabo.gateway.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

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
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();


        // React origins
        configuration.setAllowedOrigins(List.of("http://localhost", "http://localhost:5173"));


        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));


        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));


        // Needed if browser sends credentials
        configuration.setAllowCredentials(true);


        // Allow frontend to read these response headers
        configuration.setExposedHeaders(List.of("Authorization"));


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);


        return source;
    }


    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        log.info("******** SECURITY FILTER CHAIN CREATED ********");


        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)

                .cors(withDefaults())

                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/login").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .build();
    }


    @Bean
    public ReactiveUserDetailsService userDetailsService() {

        UserDetails user = User.withUsername("user123").password("{noop}password123").roles("USER").build();
        return username -> Mono.just(user).filter(u -> u.getUsername().equals(username));
    }


    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService) {


        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }
}