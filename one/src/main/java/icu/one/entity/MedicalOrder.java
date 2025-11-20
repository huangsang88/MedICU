package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MedicalOrder {
    private int orderId;
    private int patientId;
    private LocalDateTime orderTime;
    private String orderType;
    private String orderContent;
    private int doctorId;
    private String status;
    private LocalDateTime executionTime;
    private int executor;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
