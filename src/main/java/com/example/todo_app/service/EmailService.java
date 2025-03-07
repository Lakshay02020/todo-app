package com.example.todo_app.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
    void sendEmail(String toEmail, String text, String subject);
    void sendEmailWithAttachment(String toEmail, String text, String subject, MultipartFile attachment);
}
