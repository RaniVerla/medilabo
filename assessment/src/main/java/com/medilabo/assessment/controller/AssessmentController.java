package com.medilabo.assessment.controller;

import com.medilabo.assessment.dto.AssessmentResponse;
import com.medilabo.assessment.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @GetMapping("/{patientId}")
    public AssessmentResponse assess(
            @PathVariable Long patientId,
            @RequestHeader("Authorization") String authorization) {

        return assessmentService.assess(patientId, authorization);
    }

}
