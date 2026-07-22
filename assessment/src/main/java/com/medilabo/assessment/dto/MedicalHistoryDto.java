package com.medilabo.assessment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MedicalHistoryDto {

    private Long patientId;

    private List<HistoryNote> medicalHistory;
}
