package com.example.todo_app.controller;

import com.example.todo_app.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@RestController
public class FileController {

    @Autowired
    FileService fileService;

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

}
