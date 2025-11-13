package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IntakeOutput {
    private int recordId;
    private int patientId;
    private LocalDateTime recordTime;
    private String type; // 枚举值：'摄入', '排出'
    private String category; // 枚举值：'晶体', '胶体', '肠胃营养', '尿液', '其他'
    private int volumeMl;
    private String description;
    private String shiftType; // 枚举值：'白班', '夜班'
    private int nurseId;
    private LocalDateTime createdTime;
}
