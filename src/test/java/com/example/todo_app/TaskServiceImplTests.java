package com.example.todo_app;

import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceImplTests {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetTasks_WithValidPageNumberAndSize_ReturnsTasks() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 2;

        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Page<Task> pageOfTasks = new PageImpl<>(tasks);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        when(taskRepository.findAll(pageable)).thenReturn(pageOfTasks);

        // Act
        List<TaskDto> result = taskService.getTasks(pageNumber, pageSize);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1 Description", result.get(0).getTaskDescription());
        assertEquals("Task 2 Description", result.get(1).getTaskDescription());
        verify(taskRepository, times(1)).findAll(pageable);
    }

}
