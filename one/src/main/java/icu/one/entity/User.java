package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String username;
    private String password;
    private String realName;
    private String role;
    private String department;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
