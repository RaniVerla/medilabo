package com.medilabo.assessment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HistoryNote {

    private String note;

    private String physician;

    private LocalDateTime createdAt;
}