package com.example.todo_app.repository;

import com.example.todo_app.constants.TaskStatus;
import com.example.todo_app.dto.TaskDto;
import com.example.todo_app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDeadlineBetweenAndTaskStatusNot(LocalDateTime startDate, LocalDateTime endDate, TaskStatus taskStatus);
}
