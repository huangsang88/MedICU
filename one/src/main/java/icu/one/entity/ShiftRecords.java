package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShiftRecords {
    private int shiftId;
    private int patientId;
    private LocalDateTime shiftTime;
    private String shiftType;
    private int handoverNurse;
    private int takeoverNurse;
    private String patientStatus;
    private String specialNotes;
    private LocalDateTime createdTime;
}
