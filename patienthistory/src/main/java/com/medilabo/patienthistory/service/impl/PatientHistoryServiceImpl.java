package com.medilabo.patienthistory.service.impl;


import com.medilabo.patienthistory.dto.AddNoteRequest;
import com.medilabo.patienthistory.model.MedicalHistoryEntry;
import com.medilabo.patienthistory.model.PatientHistory;
import com.medilabo.patienthistory.repository.PatientHistoryRepository;
import com.medilabo.patienthistory.service.PatientHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PatientHistoryServiceImpl implements PatientHistoryService {

    private final PatientHistoryRepository repository;

    @Override
    public List<MedicalHistoryEntry> getMedicalHistory(Long patientId) {



        return repository.findByPatientId(patientId)
                .map(PatientHistory::getMedicalHistory)
                .orElse(Collections.emptyList());
    }

    @Override
    public PatientHistory addMedicalNote(Long patientId, AddNoteRequest request) {
        System.out.println("REQUEST NOTE = " + request.getNote());

        PatientHistory history = repository.findByPatientId(patientId)
                .orElseGet(() -> PatientHistory.builder()
                        .patientId(patientId)
                        .build());

        MedicalHistoryEntry entry = MedicalHistoryEntry.builder()
                .note(request.getNote())
                .physician("Dr. Smith") // temporary (later JWT)
                .createdAt(LocalDateTime.now())
                .build();

        history.getMedicalHistory().add(entry);

        return repository.save(history);
    }
}