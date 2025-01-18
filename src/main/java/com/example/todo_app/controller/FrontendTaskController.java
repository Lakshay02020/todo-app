package com.example.todo_app.controller;

import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/frontend")
public class FrontendTaskController {

    @Autowired
    TaskService taskService;

    @GetMapping(value = "/tasks")
    public String getTasks(Model model){
        log.info("Received get request");
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskDto", new TaskDto());
        return "tasks";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute("taskDto") TaskDto taskDto, Model model){
        log.info("Received task dto: {}", taskDto);
        taskService.addTask(taskDto);
        return "redirect:/frontend/tasks";  // Redirect to the task list view
    }

    @PostMapping("/task/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId, Model model){
        log.info("Task Deletion request with id: {}", taskId);
        taskService.deleteTask(taskId);
        return "redirect:/frontend/tasks";  // Redirect back to tasks view after deletion
    }
}
