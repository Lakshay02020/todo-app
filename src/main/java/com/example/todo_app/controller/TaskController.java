package com.example.todo_app.controller;

import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class TaskController {
    @Autowired
    private TaskRepository todoRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(
            @RequestParam (value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam (value = "pageSize"  , defaultValue = "5", required = false) Integer pageSize){
        List<TaskDto> tasks = taskService.getTasks(pageNumber, pageSize);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.of(Optional.of(tasks));
    }

    @PostMapping(value = "/task")
    public ResponseEntity<Task> addTask(@RequestBody TaskDto taskDto){
        log.info("Received task dto : {}", taskDto);
        return ResponseEntity.ok(taskService.addTask(taskDto));
    }

    @PutMapping(value = "/task/{taskId}")
    public ResponseEntity<Task> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long taskId){
        log.info("Task with id : {}, and request {}", taskId, taskDto);
        return ResponseEntity.ok(taskService.updateTask(taskId, taskDto));
    }

    @DeleteMapping(value = "/task/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        log.info("Task Deletion request with id: {}", taskId);
        return ResponseEntity.ok(taskService.deleteTask(taskId));
    }
}
