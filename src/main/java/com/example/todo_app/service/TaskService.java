package com.example.todo_app.service;

import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;

import java.util.List;


public interface TaskService {
    Task addTask(TaskDto taskDto);
    List<TaskDto> getTasks(Integer pageNumber, Integer pageSize);
    Task updateTask(long taskId, TaskDto taskDto);
    String deleteTask(long taskId);

    void checkTaskDeadlinesAndNotify();
}
