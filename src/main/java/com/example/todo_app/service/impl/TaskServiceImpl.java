package com.example.todo_app.service.impl;

import com.example.todo_app.constants.TaskStatus;
import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task addTask(TaskDto taskDto){
        Task task = new Task();
        task.setTaskDescription(taskDto.getTaskDescription());
        task.setTaskStatus(TaskStatus.valueOf(taskDto.getTaskStatus()));
        taskRepository.save(task);
        return task;
    }
}
