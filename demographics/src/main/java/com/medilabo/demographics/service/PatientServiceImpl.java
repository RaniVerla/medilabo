package com.medilabo.demographics.service;


import com.medilabo.demographics.dto.request.PatientRequest;
import com.medilabo.demographics.dto.response.PatientResponse;
import com.medilabo.demographics.entity.Patient;
import com.medilabo.demographics.exception.PatientNotFoundException;
import com.medilabo.demographics.mapper.PatientMapper;
import com.medilabo.demographics.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Override
    public PatientResponse addPatient(PatientRequest request) {

        Patient patient = PatientMapper.toEntity(request);
        Patient saved = repository.save(patient);

        return PatientMapper.toResponse(saved);
    }

    @Override
    public PatientResponse getPatientById(Long id) {

        Patient patient = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found: " + id));

        return PatientMapper.toResponse(patient);
    }

    @Override
    public PatientResponse updatePatient(Long id, PatientRequest request) {

        Patient existing = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found: " + id));

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setGender(request.getGender());
        existing.setAddress(request.getAddress());
        existing.setPhone(request.getPhone());

        Patient updated = repository.save(existing);

        return PatientMapper.toResponse(updated);
    }


    @Override
    public List<Patient> getAllPatients() {
        return repository.findAll();
    }
}