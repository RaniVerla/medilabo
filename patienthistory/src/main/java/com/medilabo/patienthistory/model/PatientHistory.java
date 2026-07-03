package com.medilabo.patienthistory.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "patient_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientHistory {

    @Id
    private String id;

    private Long patientId;

    @Builder.Default
    private List<MedicalHistoryEntry> medicalHistory = new ArrayList<>();
}
