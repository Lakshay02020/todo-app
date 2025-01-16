package com.example.todo_app.dto;

import lombok.Data;

@Data
public class TaskDto {
    private long taskId;
    private String taskDescription;
    private String taskStatus;
}
