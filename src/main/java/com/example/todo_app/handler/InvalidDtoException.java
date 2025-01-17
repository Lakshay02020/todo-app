package com.example.todo_app.handler;

public class InvalidDtoException extends RuntimeException {
    public InvalidDtoException(String message) {
        super(message);
    }
}
