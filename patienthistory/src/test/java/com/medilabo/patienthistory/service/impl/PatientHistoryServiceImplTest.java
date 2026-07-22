package com.medilabo.patienthistory.service.impl;

import com.medilabo.patienthistory.dto.AddNoteRequest;
import com.medilabo.patienthistory.model.MedicalHistoryEntry;
import com.medilabo.patienthistory.model.PatientHistory;
import com.medilabo.patienthistory.repository.PatientHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientHistoryServiceImplTest {

    @Mock
    private PatientHistoryRepository repository;

    @InjectMocks
    private PatientHistoryServiceImpl service;


    private PatientHistory history;


    @BeforeEach
    void setup() {

        history = PatientHistory.builder()
                .patientId(1L)
                .medicalHistory(
                        new java.util.ArrayList<>()
                )
                .build();
    }


    @Test
    void shouldReturnMedicalHistoryWhenPatientExists() {

        MedicalHistoryEntry entry =
                MedicalHistoryEntry.builder()
                        .note("Patient feels very well")
                        .physician("Dr. Smith")
                        .build();


        history.getMedicalHistory().add(entry);


        when(repository.findByPatientId(1L))
                .thenReturn(Optional.of(history));


        List<MedicalHistoryEntry> result =
                service.getMedicalHistory(1L);


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(
                "Patient feels very well",
                result.get(0).getNote()
        );


        verify(repository)
                .findByPatientId(1L);
    }


    @Test
    void shouldReturnEmptyListWhenPatientHistoryDoesNotExist() {


        when(repository.findByPatientId(1L))
                .thenReturn(Optional.empty());


        List<MedicalHistoryEntry> result =
                service.getMedicalHistory(1L);


        assertNotNull(result);
        assertTrue(result.isEmpty());


        verify(repository)
                .findByPatientId(1L);
    }


    @Test
    void shouldAddMedicalNoteToExistingPatient() {


        AddNoteRequest request =
                new AddNoteRequest();

        request.setNote(
                "The patient states that they \"feel very well.\""
        );


        when(repository.findByPatientId(1L))
                .thenReturn(Optional.of(history));


        when(repository.save(any(PatientHistory.class)))
                .thenReturn(history);


        PatientHistory result =
                service.addMedicalNote(1L, request);


        assertNotNull(result);

        assertEquals(
                1,
                result.getMedicalHistory().size()
        );


        MedicalHistoryEntry savedEntry =
                result.getMedicalHistory().get(0);


        assertEquals(
                request.getNote(),
                savedEntry.getNote()
        );


        assertEquals(
                "Dr. Smith",
                savedEntry.getPhysician()
        );


        verify(repository)
                .save(history);
    }


    @Test
    void shouldCreateNewHistoryWhenPatientDoesNotExist() {


        AddNoteRequest request =
                new AddNoteRequest();

        request.setNote(
                "Patient weight is normal"
        );


        when(repository.findByPatientId(1L))
                .thenReturn(Optional.empty());


        when(repository.save(any(PatientHistory.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );


        PatientHistory result =
                service.addMedicalNote(1L, request);


        assertNotNull(result);


        assertEquals(1L, result.getPatientId());


        assertEquals(1, result.getMedicalHistory().size()
        );


        assertEquals(
                "Patient weight is normal",
                result.getMedicalHistory()
                        .get(0)
                        .getNote()
        );


        verify(repository)
                .save(any(PatientHistory.class));
    }
}