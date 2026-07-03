package com.medilabo.patienthistory.service;

import com.medilabo.patienthistory.dto.AddNoteRequest;
import com.medilabo.patienthistory.model.MedicalHistoryEntry;
import com.medilabo.patienthistory.model.PatientHistory;

import java.util.List;

public interface PatientHistoryService {

    List<MedicalHistoryEntry> getMedicalHistory(Long patientId);

    PatientHistory addMedicalNote(Long patientId, AddNoteRequest request);
}