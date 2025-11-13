package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Permissions {
    private int permissionId;
    private int userId;
    private String module;
    private String operation;
    private LocalDateTime createdTime;
}
