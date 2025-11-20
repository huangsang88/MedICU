package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Assessment {
    private int assessmentId;
    private int patientId;
    private LocalDateTime assessmentTime;
    private String assessmentType;
    private int score;
    private int assessor;
    private String details;
    private LocalDateTime createdTime;
}
