package com.medilabo.assessment.service;

import com.medilabo.assessment.dto.HistoryNote;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class RiskCalculator {

    private static final List<String> TERMS = List.of(
            "Hemoglobin A1C",
            "Microalbumin",
            "Height",
            "Weight",
            "Smoking",
            "Abnormal",
            "Cholesterol",
            "Dizziness",
            "Relapse",
            "Reaction",
            "Antibody"

    );

    public int countTriggers(List<HistoryNote> notes) {

        int count = 0;

        for (HistoryNote note : notes) {

            String text = note.getNotes().toLowerCase();

            for (String trigger : TERMS) {

                if (text.contains(trigger.toLowerCase())) {
                    count++;
                }
            }

        }

        return count;
    }

    public int calculateAge(LocalDate dob) {

        return Period.between(dob, LocalDate.now()).getYears();

    }

    public String calculateRisk(int age,
                                String gender,
                                int triggers) {

        if (age > 30) {

            if (triggers >= 8)
                return "EARLY_ONSET";

            if (triggers >= 6)
                return "IN_DANGER";

            if (triggers >= 2)
                return "BORDERLINE";

            return "NONE";
        }

        boolean male = gender.equalsIgnoreCase("M");

        if (male) {

            if (triggers >= 5)
                return "EARLY_ONSET";

            if (triggers >= 3)
                return "IN_DANGER";

            return "NONE";
        }

        if (triggers >= 6)
            return "EARLY_ONSET";

        if (triggers >= 4)
            return "IN_DANGER";

        return "NONE";
    }

}
