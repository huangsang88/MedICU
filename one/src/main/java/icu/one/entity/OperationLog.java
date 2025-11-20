package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OperationLog {
    private int logId;
    private int userId;
    private LocalDateTime operationTime;
    private String module;
    private String operation;
    private String ipAddress;
    private LocalDateTime createdTime;
}
