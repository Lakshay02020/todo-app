package com.example.todo_app.controller;

import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping(value = "/tasks")
    public String getTasks(){
        Task task = new Task();
        task.setTaskDescription("asdada");
        todoRepository.save(task);
        return "Check";
    }
}
