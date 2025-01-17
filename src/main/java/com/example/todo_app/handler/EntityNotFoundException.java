package com.example.todo_app.handler;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException{
    private final String entityName;
    private final String entityId;

    public EntityNotFoundException(String entityName, String entityId) {
        super(String.format("%s with ID %s not found", entityName, entityId));
        this.entityName = entityName;
        this.entityId = entityId;
    }
}
