package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PatientTubesSkin {
    private int recordId;
    private int patientId;
    private LocalDateTime recordTime;
    private String type; // 枚举值：'管路', '皮肤'
    private String anatomicalLocation;
    private String description;
    private String status; // 枚举值：'正常', '异常', '风险'
    private String imageOrModelRef;
    private LocalDateTime createdTime;
}
