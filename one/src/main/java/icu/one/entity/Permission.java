package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Permission {
    private int permissionId;
    private int userId;
    private String module;
    private String operation;
    private LocalDateTime createdTime;
}
