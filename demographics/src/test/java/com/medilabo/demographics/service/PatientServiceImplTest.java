package com.medilabo.demographics.service;

import com.medilabo.demographics.dto.request.PatientRequest;
import com.medilabo.demographics.dto.response.PatientResponse;
import com.medilabo.demographics.entity.Patient;
import com.medilabo.demographics.exception.PatientNotFoundException;
import com.medilabo.demographics.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientServiceImpl service;

    private Patient patient;
    private PatientRequest request;

    @BeforeEach
    void setUp() {

        patient = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender("Male")
                .address("Dallas")
                .phone("1234567890")
                .build();

        request = new PatientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setGender("Male");
        request.setAddress("Dallas");
        request.setPhone("1234567890");
    }

    @Test
    void shouldAddPatient() {

        when(repository.save(any(Patient.class))).thenReturn(patient);

        PatientResponse response = service.addPatient(request);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());

        verify(repository, times(1)).save(any(Patient.class));
    }

    @Test
    void shouldReturnPatientById() {

        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        PatientResponse response = service.getPatientById(1L);

        assertEquals(1L, response.getId());
        assertEquals("John", response.getFirstName());

        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {

        when(repository.findById(100L)).thenReturn(Optional.empty());

        PatientNotFoundException exception =
                assertThrows(PatientNotFoundException.class,
                        () -> service.getPatientById(100L));

        assertEquals("Patient not found: 100", exception.getMessage());

        verify(repository).findById(100L);
    }

    @Test
    void shouldUpdatePatient() {

        when(repository.findById(1L)).thenReturn(Optional.of(patient));
        when(repository.save(any(Patient.class))).thenReturn(patient);

        PatientResponse response = service.updatePatient(1L, request);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());

        verify(repository).findById(1L);
        verify(repository).save(any(Patient.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingPatient() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class,
                () -> service.updatePatient(1L, request));

        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldReturnAllPatients() {

        when(repository.findAll()).thenReturn(List.of(patient));

        List<Patient> patients = service.getAllPatients();

        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getFirstName());

        verify(repository).findAll();
    }
}