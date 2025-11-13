package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NursingRecords {
    private int recordId;
    private int patientId;
    private LocalDateTime recordTime;
    private String recordType;
    private String content;
    private int nurseId;
    private String signature;
    private LocalDateTime createdTime;
}
