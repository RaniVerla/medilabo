package com.medilabo.demographics.mapper;


import com.medilabo.demographics.dto.request.PatientRequest;
import com.medilabo.demographics.dto.response.PatientResponse;
import com.medilabo.demographics.entity.Patient;

public class PatientMapper {

    // Request → Entity
    public static Patient toEntity(PatientRequest request) {
        if (request == null) return null;

        return Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();
    }

    // Entity → Response
    public static PatientResponse toResponse(Patient patient) {
        if (patient == null) return null;

        return PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .phone(patient.getPhone())
                .build();
    }
}