package icu.one.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Statistic {
    private int statId;
    private LocalDate statDate;
    private String statType;
    private String department;
    private double value;
    private LocalDateTime createdTime;
}
