package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VitalSign {
    private int recordId;
    private int patientId;
    private LocalDateTime recordTime;
    private double temperature;
    private int heartRate;
    private int systolicBp;
    private int diastolicBp;
    private int respiratoryRate;
    private double spo2;
    private int nurseId;
    private LocalDateTime createdTime;
}
