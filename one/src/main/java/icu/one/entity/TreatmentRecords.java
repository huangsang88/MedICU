package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TreatmentRecords {
    private int treatmentId;
    private int patientId;
    private LocalDateTime treatmentTime;
    private String treatmentType;
    private int doctorId;
    private String content;
    private String outcome;
    private LocalDateTime createdTime;
}
