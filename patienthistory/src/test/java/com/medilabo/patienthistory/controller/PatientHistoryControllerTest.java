package com.medilabo.patienthistory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patienthistory.dto.AddNoteRequest;
import com.medilabo.patienthistory.model.MedicalHistoryEntry;
import com.medilabo.patienthistory.model.PatientHistory;
import com.medilabo.patienthistory.service.PatientHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PatientHistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientHistoryControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @MockitoBean
    private PatientHistoryService service;



    @Test
    void shouldGetMedicalHistory() throws Exception {


        MedicalHistoryEntry entry =
                MedicalHistoryEntry.builder()
                        .note("The patient states that they \"feel very well.\"")
                        .physician("Dr. Smith")
                        .createdAt(LocalDateTime.now())
                        .build();


        when(service.getMedicalHistory(1L))
                .thenReturn(List.of(entry));


        mockMvc.perform(
                        get("/api/v2/patients/1/medical-history")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].note")
                        .value("The patient states that they \"feel very well.\""))
                .andExpect(jsonPath("$[0].physician")
                        .value("Dr. Smith"));
    }



    @Test
    void shouldAddMedicalNote() throws Exception {


        AddNoteRequest request =
                new AddNoteRequest();

        request.setNote(
                "The patient states that they \"feel very well.\""
        );


        PatientHistory response =
                PatientHistory.builder()
                        .patientId(1L)
                        .medicalHistory(
                                List.of(
                                        MedicalHistoryEntry.builder()
                                                .note(request.getNote())
                                                .physician("Dr. Smith")
                                                .createdAt(LocalDateTime.now())
                                                .build()
                                )
                        )
                        .build();



        when(service.addMedicalNote(
                eq(1L),
                any(AddNoteRequest.class)
        ))
                .thenReturn(response);



        mockMvc.perform(
                        post("/api/v2/patients/1/medical-history")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId")
                        .value(1))
                .andExpect(jsonPath("$.medicalHistory[0].note")
                        .value(
                                "The patient states that they \"feel very well.\""
                        ))
                .andExpect(jsonPath("$.medicalHistory[0].physician")
                        .value("Dr. Smith"));
    }
}