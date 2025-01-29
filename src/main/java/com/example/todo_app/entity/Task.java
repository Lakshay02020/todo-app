package com.example.todo_app.entity;

import com.example.todo_app.constants.Priority;
import com.example.todo_app.constants.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "todo")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    private Priority taskPriority;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deadline", nullable = true)
    private LocalDateTime deadline; // Deadline for the task

    // Self-referencing Relationship for Subtasks
    @ManyToOne
    @JoinColumn(name = "parent_task_id") // Creates a foreign key column in the database
    @JsonBackReference // Prevents infinite recursion
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference // Prevents infinite recursion
    private List<Task> subtasks; // List of subtasks
}
