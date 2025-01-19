package com.example.todo_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TaskDto {
    private long taskId;
    private String taskDescription;
    private String taskStatus;
    private String taskPriority;
}
