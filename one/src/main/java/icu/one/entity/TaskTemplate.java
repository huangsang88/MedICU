package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskTemplate {
    private int templateId;
    private String taskName;
    private String description;
    private String taskType;
    private String frequency;
    private String department;
    private String triggerCondition;
    private String defaultAssigneeRole;
    private int estimatedDuration;
    private boolean active;
    private int createdBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
