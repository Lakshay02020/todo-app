package com.example.todo_app.controller;

import com.example.todo_app.entity.Task;
import com.example.todo_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendTaskController {

    @Autowired
    TaskService taskService;

    @GetMapping(value = "/tasks")
    public String getTasks(Model model){
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
}
