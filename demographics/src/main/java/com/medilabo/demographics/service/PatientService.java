package com.medilabo.demographics.service;


import com.medilabo.demographics.dto.request.PatientRequest;
import com.medilabo.demographics.dto.response.PatientResponse;
import com.medilabo.demographics.entity.Patient;

import java.util.List;

public interface PatientService {

    PatientResponse addPatient(PatientRequest request);

    PatientResponse getPatientById(Long id);

    PatientResponse updatePatient(Long id, PatientRequest request);

    List<Patient> getAllPatients();
}