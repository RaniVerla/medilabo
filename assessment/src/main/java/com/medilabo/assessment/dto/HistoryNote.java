package com.medilabo.assessment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryNote {

    private String note;

    private String physician;

    private LocalDateTime createdAt;
}