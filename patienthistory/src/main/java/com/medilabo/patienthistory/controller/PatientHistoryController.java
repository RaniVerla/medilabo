package com.medilabo.patienthistory.controller;

import com.medilabo.patienthistory.dto.AddNoteRequest;
import com.medilabo.patienthistory.model.MedicalHistoryEntry;
import com.medilabo.patienthistory.model.PatientHistory;
import com.medilabo.patienthistory.service.PatientHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/patients")
@RequiredArgsConstructor
public class PatientHistoryController {

    private final PatientHistoryService service;

    @GetMapping("/{patientId}/medical-history")
    public List<MedicalHistoryEntry> getMedicalHistory(
            @PathVariable Long patientId) {

        return service.getMedicalHistory(patientId);
    }

    @PostMapping("/{patientId}/medical-history")
    public PatientHistory addMedicalNote(
            @PathVariable Long patientId,
            @RequestBody AddNoteRequest request) {

        return service.addMedicalNote(patientId, request);
    }
}