package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskReminders {
    private int reminderId;
    private int taskId;
    private int userId;
    private LocalDateTime reminderTime;
    private String message;
    private String reminderType;
    private String status;
    private LocalDateTime sentTime;
    private LocalDateTime createdTime;
}
