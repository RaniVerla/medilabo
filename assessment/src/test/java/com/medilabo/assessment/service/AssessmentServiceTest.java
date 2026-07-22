package com.medilabo.assessment.service;

import com.medilabo.assessment.client.HistoryClient;
import com.medilabo.assessment.client.PatientClient;
import com.medilabo.assessment.dto.AssessmentResponse;
import com.medilabo.assessment.dto.HistoryNote;
import com.medilabo.assessment.dto.PatientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {


    @Mock
    private PatientClient patientClient;


    @Mock
    private HistoryClient historyClient;


    @Mock
    private RiskCalculator calculator;


    @InjectMocks
    private AssessmentService service;


    @Test
    void shouldGenerateAssessmentSuccessfully() {


        Long patientId = 1L;

        String token = "Bearer test-token";


        PatientDto patient = PatientDto.builder().id(1L).firstName("John").lastName("Smith").gender("Male").dateOfBirth(LocalDate.of(1990, 1, 1)).build();


        List<HistoryNote> history = List.of(HistoryNote.builder().note("Patient feels thirsty").build());


        when(patientClient.getPatient(patientId, token)).thenReturn(patient);


        when(historyClient.getHistory(patientId, token)).thenReturn(history);


        when(calculator.calculateAge(patient.getDateOfBirth())).thenReturn(35);


        when(calculator.countTriggers(history)).thenReturn(1);


        when(calculator.calculateRisk(35, "Male", 1)).thenReturn("None");


        AssessmentResponse response = service.assess(patientId, token);


        assertNotNull(response);

        assertEquals(1L, response.getPatientId());

        assertEquals("John Smith", response.getName());

        assertEquals(35, response.getAge());

        assertEquals("Male", response.getGender());

        assertEquals(1, response.getTriggerCount());

        assertEquals("None", response.getRiskLevel());


        verify(patientClient).getPatient(patientId, token);


        verify(historyClient).getHistory(patientId, token);


        verify(calculator).calculateRisk(35, "Male", 1);
    }


    @Test
    void shouldReturnEarlyOnsetRisk() {


        Long patientId = 1L;

        String token = "Bearer token";


        PatientDto patient = PatientDto.builder().id(1L).firstName("Mary").lastName("Jones").gender("Female").dateOfBirth(LocalDate.of(2005, 1, 1)).build();


        List<HistoryNote> history = List.of(HistoryNote.builder().note("too much thirst").build());


        when(patientClient.getPatient(patientId, token)).thenReturn(patient);


        when(historyClient.getHistory(patientId, token)).thenReturn(history);


        when(calculator.calculateAge(patient.getDateOfBirth())).thenReturn(21);


        when(calculator.countTriggers(history)).thenReturn(5);


        when(calculator.calculateRisk(21, "Female", 5)).thenReturn("Early Onset");


        AssessmentResponse response = service.assess(patientId, token);


        assertEquals("Early Onset", response.getRiskLevel());
    }
}