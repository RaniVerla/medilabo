package com.medilabo.assessment.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AssessmentResponse {

    private Long patientId;

    private String name;

    private int age;

    private String gender;

    private int triggerCount;

    private String riskLevel;
}
