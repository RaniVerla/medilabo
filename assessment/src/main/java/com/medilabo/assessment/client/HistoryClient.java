package com.medilabo.assessment.client;

import com.medilabo.assessment.dto.HistoryNote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryClient {

    private final WebClient.Builder builder;

    @Value("${services.history.url}")
    private String baseUrl;


    public List<HistoryNote> getHistory(Long id, String token) {

        return builder.build()
                .get()
                .uri(baseUrl + "/api/v2/patients/" + id + "/medical-history")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToFlux(HistoryNote.class)
                .collectList()
                .block();
    }
}