package com.example.todo_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TaskDto {
    private Long taskId;
    private String taskDescription;
    private String taskStatus;
    private String taskPriority;
    private LocalDateTime deadline; // Deadline for the task
    private Long parentTaskId;
    private List<TaskDto> subtasks;
}
