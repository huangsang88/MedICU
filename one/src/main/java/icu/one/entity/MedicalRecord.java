package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MedicalRecord {
    private int recordId;
    private int patientId;
    private String recordType;
    private LocalDateTime recordTime;
    private String content;
    private int doctorId;
    private String department;
    private LocalDateTime createdTime;
}
