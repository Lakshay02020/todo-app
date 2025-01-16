package com.example.todo_app.service;

import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import org.springframework.stereotype.Service;


public interface TaskService {
    Task addTask(TaskDto taskDto);
}
