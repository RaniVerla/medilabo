package com.medilabo.assessment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryNote {

    private String physician;

    private String note;

    private LocalDateTime createdAt;
}