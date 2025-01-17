package com.example.todo_app.controller;

import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TaskController {
    @Autowired
    private TaskRepository todoRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/tasks")
    public List<Task> getTasks(){
        return taskService.getTasks();
    }

    @PostMapping(value = "/task")
    public Task addTask(@RequestBody TaskDto taskDto){
        log.info("Received task dto : {}", taskDto);
        return taskService.addTask(taskDto);
    }

    @PutMapping(value = "/task/{taskId}")
    public Task updateTask(@RequestBody TaskDto taskDto, @PathVariable Long taskId){
        log.info("Task with id : {}, and request {}", taskId, taskDto);
        return taskService.updateTask(taskId, taskDto);
    }

    @DeleteMapping(value = "/task/{taskId}")
    public String deleteTask(@PathVariable Long taskId){
        log.info("Task Deletion request with id: {}", taskId);
        return taskService.deleteTask(taskId);
    }
}
