package com.medilabo.assessment.dto;

import lombok.Data;

import java.util.List;

@Data
public class MedicalHistoryDto {

    private Long patientId;

    private List<HistoryNote> medicalHistory;
}
