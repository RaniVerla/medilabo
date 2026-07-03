package com.medilabo.patienthistory.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddNoteRequest {

    @NotBlank(message = "Note cannot be empty")
    private String note;
}
