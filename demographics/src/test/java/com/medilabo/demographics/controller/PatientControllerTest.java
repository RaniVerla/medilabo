package com.medilabo.demographics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.demographics.dto.request.PatientRequest;
import com.medilabo.demographics.dto.response.PatientResponse;
import com.medilabo.demographics.entity.Patient;
import com.medilabo.demographics.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PatientService service;

    @Test
    @WithMockUser
    void shouldAddPatient() throws Exception {

        PatientRequest request = new PatientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setGender("Male");
        request.setAddress("Dallas");
        request.setPhone("1234567890");

        PatientResponse response = new PatientResponse();
        response.setId(1L);
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setDateOfBirth(LocalDate.of(1990, 1, 1));
        response.setGender("Male");
        response.setAddress("Dallas");
        response.setPhone("1234567890");

        when(service.addPatient(any(PatientRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    @WithMockUser
    void shouldReturnPatientById() throws Exception {

        PatientResponse response = new PatientResponse();
        response.setId(1L);
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setDateOfBirth(LocalDate.of(1990, 1, 1));
        response.setGender("Male");
        response.setAddress("Dallas");
        response.setPhone("1234567890");

        when(service.getPatientById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @WithMockUser
    void shouldUpdatePatient() throws Exception {

        PatientRequest request = new PatientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setGender("Male");
        request.setAddress("Dallas");
        request.setPhone("1234567890");

        PatientResponse response = new PatientResponse();
        response.setId(1L);
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setDateOfBirth(LocalDate.of(1990, 1, 1));
        response.setGender("Male");
        response.setAddress("Dallas");
        response.setPhone("1234567890");

        when(service.updatePatient(eq(1L), any(PatientRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @WithMockUser
    void shouldReturnAllPatients() throws Exception {

        Patient patient = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender("Male")
                .address("Dallas")
                .phone("1234567890")
                .build();

        when(service.getAllPatients())
                .thenReturn(List.of(patient));

        mockMvc.perform(get("/api/v1/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }
}