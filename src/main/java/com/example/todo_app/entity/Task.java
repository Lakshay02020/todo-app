package com.example.todo_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "todo")
public class Task {
    @Id
    private long taskId;
    private String taskDescription;
}
