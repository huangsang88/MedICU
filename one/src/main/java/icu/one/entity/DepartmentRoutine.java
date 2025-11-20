package icu.one.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class DepartmentRoutine {
    private int routineId;
    private String department;
    private String routineName;
    private String description;
    private String triggerEvent;
    private LocalTime triggerTime;
    private int delayMinutes;
    private int templateId;
    private boolean active;
    private int createdBy;
    private LocalDateTime createdTime;
}
