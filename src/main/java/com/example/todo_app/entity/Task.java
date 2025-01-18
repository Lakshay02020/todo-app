package com.example.todo_app.entity;

import com.example.todo_app.constants.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "todo")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
}
