package icu.one.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SystemConfig {
    private int configId;
    private String configKey;
    private String configValue;
    private String description;
    private LocalDateTime updateTime;
}
