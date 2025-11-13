package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Bed {
    private int bedId;
    private String bedNo;
    private String roomNo;
    private String department;
    private String status;
    private int patientId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
