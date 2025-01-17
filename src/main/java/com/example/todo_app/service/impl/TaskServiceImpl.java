package com.example.todo_app.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.example.todo_app.constants.TaskStatus;
import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> getTasks(){
        List<Task> tasks = taskRepository.findAll();
        log.info("Tasks: {}", tasks);
        return tasks;
    }

    @Override
    public Task addTask(TaskDto taskDto){
        Task task = new Task();
        task.setTaskDescription(taskDto.getTaskDescription());
        task.setTaskStatus(TaskStatus.valueOf(taskDto.getTaskStatus()));
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task updateTask(long taskId ,TaskDto taskDto){
        Optional<Task> getTask = taskRepository.findById(taskId);
        if(getTask.isEmpty()){
            // Throw Error
            // Invalid Request
        }else {
            Task task = getTask.get();
            if(!StringUtil.isNullOrEmpty(taskDto.getTaskDescription())){
                log.info("Updated Description");
                task.setTaskDescription(taskDto.getTaskDescription());
            }

            if(!StringUtil.isNullOrEmpty(taskDto.getTaskStatus())){
                log.info("Updated Status");
                task.setTaskStatus(TaskStatus.valueOf(taskDto.getTaskStatus()));
            }

            taskRepository.save(task);
            log.info("Update Successful: {}", task);
            return task;
        }

        log.info("Update unsuccessful");
        return null;
    }

    @Override
    public String deleteTask(long taskId){
        Optional<Task> getTask = taskRepository.findById(taskId);
        if(getTask.isEmpty()){
            // Throw Error
            // Invalid Request
            return "Task with Task Id not found";
        }

        taskRepository.delete(getTask.get());
        return "Task Deleted Successfully";
    }
}
