package com.medilabo.patienthistory.repository;

import com.medilabo.patienthistory.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientHistoryRepository
        extends MongoRepository<PatientHistory, String> {

    Optional<PatientHistory> findByPatientId(Long patientId);
}
