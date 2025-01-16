package com.example.todo_app.repository;

import com.example.todo_app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Task, Long> {
}
