package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Tasks {
    private int taskId;
    private int templateId;
    private int patientId;
    private String taskName;
    private String description;
    private int assignedTo;
    private LocalDateTime scheduledTime;
    private LocalDateTime dueTime;
    private String status;
    private String priority;
    private LocalDateTime completionTime;
    private String completionNotes;
    private int createdBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
