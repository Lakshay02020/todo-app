package com.example.todo_app.controller;

import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TaskController {
    @Autowired
    private TaskRepository todoRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/tasks")
    public String getTasks(){
        Task task = new Task();
//        task.setTaskId(122);
        task.setTaskDescription("asdada");
        todoRepository.save(task);
        return "Check";
    }

    @PostMapping(value = "/task")
    public Task addTask(@RequestBody TaskDto taskDto){
        log.info("Received task dto : {}", taskDto);
        return taskService.addTask(taskDto);
    }
}
