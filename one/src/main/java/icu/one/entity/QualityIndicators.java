package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class QualityIndicators {
    private int indicatorId;
    private String indicatorName;
    private double targetValue;
    private double actualValue;
    private String calculationPeriod;
    private String department;
    private LocalDateTime updateTime;
}
