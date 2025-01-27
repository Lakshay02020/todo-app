package com.example.todo_app.converter;

import com.example.todo_app.constants.Priority;
import com.example.todo_app.constants.TaskStatus;
import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {

    @Autowired
    TaskRepository taskRepository;

    public Task dtoToEntity(TaskDto taskDto){
        Task task = new Task();
        task.setTaskDescription(taskDto.getTaskDescription());
        task.setTaskStatus(TaskStatus.valueOf(taskDto.getTaskStatus()));
        task.setTaskPriority(Priority.valueOf(taskDto.getTaskPriority()));
        task.setDeadline(taskDto.getDeadline());
        return task;
    }

    public static TaskDto entityToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskDescription(task.getTaskDescription());
        taskDto.setTaskStatus(task.getTaskStatus().name()); // Convert enum to String
        taskDto.setTaskPriority(task.getTaskPriority().name()); // Convert enum to String
        taskDto.setDeadline(task.getDeadline());
        return taskDto;
    }

}
