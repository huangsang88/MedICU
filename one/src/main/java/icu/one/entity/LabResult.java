package icu.one.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LabResult {
    private int resultId;
    private int patientId;
    private LocalDateTime testTime;
    private String testType; // 枚举值：'血气', '血糖', '电解质', '其他'
    private String parameterName;
    private BigDecimal value;
    private String unit;
    private String referenceRange;
    private LocalDateTime createdTime;
}
