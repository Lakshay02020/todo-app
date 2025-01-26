package com.example.todo_app.service.impl;

import com.example.todo_app.entity.Task;
import com.example.todo_app.helper.Helper;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public ByteArrayInputStream getExcel(){
        List<Task> tasks = taskRepository.findAll();
        return Helper.dataToExcel(tasks);
    }
}
