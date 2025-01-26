package com.example.todo_app.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.example.todo_app.constants.Priority;
import com.example.todo_app.constants.TaskStatus;
import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.handler.EntityNotFoundException;

import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> getTasks(Integer pageNumber, Integer pageSize){
        // Pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Task> pagesOfTask = taskRepository.findAll(pageable);

        List<Task> tasks = pagesOfTask.getContent();
        log.info("List of tasks on page no {}, Tasks: {}", pageNumber, tasks);

        return tasks;
    }

    @Override
    public Task addTask(TaskDto taskDto){
        try {
            Task task = new Task();
            task.setTaskDescription(taskDto.getTaskDescription());
            task.setTaskStatus(TaskStatus.valueOf(taskDto.getTaskStatus()));
            task.setTaskPriority(Priority.valueOf(taskDto.getTaskPriority()));
            taskRepository.save(task);
            return task;
        } catch (IllegalArgumentException e) {
            log.error("Invalid task data provided: {}", taskDto, e);
            throw e; // Re-throw exception for proper error handling
        } catch (Exception e) {
            log.error("Unexpected error while saving task: {}", taskDto, e);
            throw new RuntimeException("Failed to add task. Please try again later.", e);
        }
    }

    @Override
    public Task updateTask(long taskId ,TaskDto taskDto){
        Optional<Task> getTask = taskRepository.findById(taskId);

        if(getTask.isEmpty()){
            throw new EntityNotFoundException("Task", String.valueOf(taskId));
        }

        Task task = getTask.get();
        if(!StringUtil.isNullOrEmpty(taskDto.getTaskDescription())){
            task.setTaskDescription(taskDto.getTaskDescription());
        }

        if(!StringUtil.isNullOrEmpty(taskDto.getTaskStatus())){
            task.setTaskStatus(TaskStatus.valueOf(taskDto.getTaskStatus()));
        }

        taskRepository.save(task);
        log.info("Update Successful: {}", task);
        return task;
    }

    @Override
    public String deleteTask(long taskId){
        Optional<Task> getTask = taskRepository.findById(taskId);

        if(getTask.isEmpty()){
            throw new EntityNotFoundException("Task", String.valueOf(taskId));
        }

        taskRepository.delete(getTask.get());
        return "Task Deleted Successfully";
    }
}
