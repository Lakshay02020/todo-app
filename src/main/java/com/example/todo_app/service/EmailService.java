package com.example.todo_app.service;

public interface EmailService {
    void sendEmail(String toEmail, String text, String subject);
}
