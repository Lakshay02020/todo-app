package com.example.todo_app.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.example.todo_app.constants.Priority;
import com.example.todo_app.constants.TaskStatus;
import com.example.todo_app.converter.TaskConverter;
import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.handler.EntityNotFoundException;

import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.EmailService;
import com.example.todo_app.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.todo_app.constants.AppConstants.TASK_ENTITY;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskConverter taskConverter;

    @Autowired
    EmailService emailService;

    @Override
    public List<TaskDto> getTasks(Integer pageNumber, Integer pageSize){
        // Pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Task> pagesOfTask = taskRepository.findAll(pageable);
        List<Task> tasks = pagesOfTask.getContent();
        // Convert to Dto
        List<TaskDto> taskDtos = new ArrayList<>();

        for(Task task: tasks){
            TaskDto taskDto = TaskConverter.entityToDto(task);
            List<TaskDto> subTasks = getSubTasks(task);
            taskDto.setSubtasks(subTasks);
            taskDtos.add(taskDto);
        }

        log.info("List of tasks on page no {}, Tasks: {}", pageNumber, taskDtos);

        return taskDtos;
    }

    @Override
    public Task addTask(TaskDto taskDto){
        try {
            log.info("Request to add task received: {}", taskDto);
            Task newTask = taskConverter.dtoToEntity(taskDto);
            if(taskDto.getParentTaskId() != null){
                Optional<Task> parentTask = taskRepository.findById(taskDto.getParentTaskId());
                if(parentTask.isPresent()){
                    newTask.setParentTask(parentTask.get());
                    List<Task> subTasksOfParentTask = parentTask.get().getSubtasks();
                    parentTask.get().setSubtasks(subTasksOfParentTask);
                }else
                    throw new EntityNotFoundException(TASK_ENTITY, taskDto.getParentTaskId().toString());
            }
            taskRepository.save(newTask);
            return newTask;
        } catch (IllegalArgumentException e) {
            log.error("Invalid task data provided: {}", taskDto, e);
            throw e; // Re-throw exception for proper error handling
        } catch (Exception e) {
            log.error("Unexpected error while saving task: {}", taskDto, e);
            throw new RuntimeException("Failed to add task. Please try again later.", e);
        }
    }

    @Override
    public Task updateTask(Long taskId ,TaskDto taskDto){
        Optional<Task> getTask = taskRepository.findById(taskId);

        if(getTask.isEmpty()){
            throw new EntityNotFoundException(TASK_ENTITY, String.valueOf(taskId));
        }

        Task task = getTask.get();
        if(!StringUtil.isNullOrEmpty(taskDto.getTaskDescription())){
            task.setTaskDescription(taskDto.getTaskDescription());
        }

        if(!StringUtil.isNullOrEmpty(taskDto.getTaskStatus())){
            task.setTaskStatus(TaskStatus.valueOf(taskDto.getTaskStatus()));
        }

        if (!StringUtil.isNullOrEmpty(taskDto.getTaskPriority())) {
            task.setTaskPriority(Priority.valueOf(taskDto.getTaskPriority()));
        }

        if (taskDto.getDeadline() != null) {
            task.setDeadline(taskDto.getDeadline());
        }

        taskRepository.save(task);
        log.info("Update Successful: {}", task);
        return task;
    }

    @Override
    public String deleteTask(Long taskId){
        Optional<Task> getTask = taskRepository.findById(taskId);

        if(getTask.isEmpty()){
            throw new EntityNotFoundException(TASK_ENTITY, String.valueOf(taskId));
        }

        taskRepository.delete(getTask.get());
        return "Task Deleted Successfully";
    }

    @Override
    @Scheduled(fixedRate = 1800000)
    public void checkTaskDeadlinesAndNotify(){
        log.info("Deadline checker Running");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourFromNow = now.plusHours(1);

        List<Task> tasks = taskRepository.findByDeadlineBetweenAndTaskStatusNot(now, oneHourFromNow, TaskStatus.COMPLETED);
        for(Task task: tasks){
            log.info("Task with task id: {} and current status: {}", task.getTaskId(), task.getTaskStatus());

            String subject = "⏳ Reminder: Upcoming Task Deadline - " + task.getTaskDescription();
            String text = "Dear User,\n\n"
                    + "This is a friendly reminder that your task **\"" + task.getTaskDescription() + "\"** "
                    + "is approaching its deadline on **" + task.getDeadline() + "**.\n\n"
                    + "Please ensure that you complete it on time to stay on track.\n\n"
                    + "Best regards,\n"
                    + "Your TODO App Team";

            emailService.sendEmail("xyz@gmail.com", text, subject);
            log.info("Email Sent successfully");
        }
    };

    public List<TaskDto> getSubTasks(Task task){
        List<Task> tasks = taskRepository.findAllByParentTask(task);
        return tasks.stream().map(TaskConverter::entityToDto).toList();
    }
}
