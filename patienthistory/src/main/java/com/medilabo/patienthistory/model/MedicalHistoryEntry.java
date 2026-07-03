package com.medilabo.patienthistory.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryEntry {

    private String note;

    private String physician;

    private LocalDateTime createdAt;
}
