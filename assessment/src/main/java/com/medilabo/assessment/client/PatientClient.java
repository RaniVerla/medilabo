package com.medilabo.assessment.client;


import com.medilabo.assessment.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PatientClient {

    private final WebClient.Builder builder;

    @Value("${services.demographics.url}")
    private String baseUrl;

    public PatientDto getPatient(Long id, String token) {

        return builder.build()
                .get()
                .uri(baseUrl + "/api/v1/patients/" + id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(PatientDto.class)
                .block();
    }
}