package com.example.todo_app.controller;

import com.example.todo_app.service.EmailService;
import com.example.todo_app.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    EmailService emailService;

    @GetMapping("/resource")
    public ResponseEntity<Resource> download() {
        ByteArrayInputStream byteArrayInputStream = fileService.getExcel();
        InputStreamResource file = new InputStreamResource(byteArrayInputStream);

        ResponseEntity<Resource> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tasks.xlsx") // Sets file name for download
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")) // Sets the correct MIME type for Excel files
                .body(file); // Includes the file in the response body

        log.info("Response: {}", response);
        return response;
    }

    @PostMapping(value = "/sendEmail", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> sendMail(
            @RequestParam String toEmail,
            @RequestParam String text,
            @RequestParam String subject,
            @RequestPart(required = false) MultipartFile resource) {
        try {
            log.info("Request received to send email to {} with subject {} and text {}", toEmail, text, subject);
            if (resource != null && !resource.isEmpty())
                emailService.sendEmailWithAttachment(toEmail, text, subject, resource);
            else
                emailService.sendEmail(toEmail, text, subject);
            return ResponseEntity.ok("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            // Log the error for debugging purposes
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }

}
