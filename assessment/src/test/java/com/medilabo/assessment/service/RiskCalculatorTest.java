package com.medilabo.assessment.service;

import com.medilabo.assessment.dto.HistoryNote;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskCalculatorTest {


    private final RiskCalculator calculator = new RiskCalculator();


    @Test
    void shouldCountTriggerTermsFromHistoryNotes() {

        List<HistoryNote> notes = List.of(HistoryNote.builder().note("Hemoglobin A1C is high").build(),

                HistoryNote.builder().note("Patient has abnormal Cholesterol").build());


        int result = calculator.countTriggers(notes);


        assertEquals(3, result);
    }


    @Test
    void shouldReturnZeroWhenNoTriggersFound() {

        List<HistoryNote> notes = List.of(HistoryNote.builder().note("Patient feels very well").build());


        int result = calculator.countTriggers(notes);


        assertEquals(0, result);
    }


    @Test
    void shouldCalculateAgeCorrectly() {


        LocalDate dob = LocalDate.now().minusYears(40);


        int age = calculator.calculateAge(dob);


        assertEquals(40, age);
    }


    // -------- AGE > 30 RULES --------
    @Test
    void shouldReturnNoneForOlderPatientWithLessThanTwoTriggers() {

        String risk = calculator.calculateRisk(45, "M", 1);


        assertEquals("NONE", risk);
    }


    @Test
    void shouldReturnBorderlineForOlderPatient() {

        String risk = calculator.calculateRisk(45, "M", 3);


        assertEquals("BORDERLINE", risk);
    }


    @Test
    void shouldReturnInDangerForOlderPatient() {

        String risk = calculator.calculateRisk(45, "M", 6);


        assertEquals("IN_DANGER", risk);
    }


    @Test
    void shouldReturnEarlyOnsetForOlderPatient() {

        String risk = calculator.calculateRisk(45, "M", 8);


        assertEquals("EARLY_ONSET", risk);
    }


    // -------- UNDER 30 MALE RULES --------
    @Test
    void shouldReturnInDangerForYoungMale() {

        String risk = calculator.calculateRisk(25, "M", 3);


        assertEquals("IN_DANGER", risk);
    }


    @Test
    void shouldReturnEarlyOnsetForYoungMale() {

        String risk = calculator.calculateRisk(25, "M", 5);


        assertEquals("EARLY_ONSET", risk);
    }


    // -------- UNDER 30 FEMALE RULES --------
    @Test
    void shouldReturnInDangerForYoungFemale() {

        String risk = calculator.calculateRisk(25, "F", 4);


        assertEquals("IN_DANGER", risk);
    }


    @Test
    void shouldReturnEarlyOnsetForYoungFemale() {

        String risk = calculator.calculateRisk(25, "F", 6);


        assertEquals("EARLY_ONSET", risk);
    }

}