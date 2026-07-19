package com.medilabo.assessment.service;

import com.medilabo.assessment.client.HistoryClient;
import com.medilabo.assessment.client.PatientClient;
import com.medilabo.assessment.dto.AssessmentResponse;
import com.medilabo.assessment.dto.HistoryNote;
import com.medilabo.assessment.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final PatientClient patientClient;

    private final HistoryClient historyClient;

    private final RiskCalculator calculator;


    public AssessmentResponse assess(Long patientId, String token) {

        PatientDto patient =
                patientClient.getPatient(patientId, token);


        List<HistoryNote> history =
                historyClient.getHistory(patientId, token);


        int age =
                calculator.calculateAge(patient.getDateOfBirth());


        int triggers =
                calculator.countTriggers(history);


        String risk =
                calculator.calculateRisk(
                        age,
                        patient.getGender(),
                        triggers);


        return AssessmentResponse.builder()
                .patientId(patient.getId())
                .name(patient.getFirstName() + " " + patient.getLastName())
                .age(age)
                .gender(patient.getGender())
                .triggerCount(triggers)
                .riskLevel(risk)
                .build();

    }
}