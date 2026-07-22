package com.medilabo.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.assessment.dto.AssessmentResponse;
import com.medilabo.assessment.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AssessmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class AssessmentControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @MockitoBean
    private AssessmentService assessmentService;



    @Test
    void shouldReturnAssessment() throws Exception {


        AssessmentResponse response =
                AssessmentResponse.builder()
                        .patientId(1L)
                        .riskLevel("Borderline")
                        .build();


        when(assessmentService.assess(
                eq(1L),
                eq("Bearer test-token")
        ))
                .thenReturn(response);



        mockMvc.perform(
                        get("/api/v3/assessments/1")
                                .header(
                                        "Authorization",
                                        "Bearer test-token"
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId")
                        .value(1))
                .andExpect(jsonPath("$.riskLevel")
                        .value("Borderline"));
    }



    @Test
    void shouldReturnUnauthorizedWhenAuthorizationHeaderMissing()
            throws Exception {


        mockMvc.perform(
                        get("/api/v3/assessments/1")
                )
                .andExpect(status().isBadRequest());
    }
}