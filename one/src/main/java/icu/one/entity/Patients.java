package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Patients {
    private int patientId;
    private String medicalRecordNo;
    private String name;
    private String gender;
    private int age;
    private LocalDateTime admissionTime;
    private LocalDateTime dischargeTime;
    private String diagnosis;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
