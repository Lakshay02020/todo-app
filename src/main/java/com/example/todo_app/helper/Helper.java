package com.example.todo_app.helper;

import com.example.todo_app.entity.Task;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.jdbc.Work;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Helper {
    public static String[] HEADERS = {
        "taskDescription",
        "taskStatus",
        "taskPriority"
    };

    public static String SHEET_NAME =  "todo_list";
    public static Integer HEADER_ROW = 0;

    public static ByteArrayInputStream dataToExcel(List<Task> tasks){
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            //Create Sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            //Header rows
            Row row = sheet.createRow(HEADER_ROW);

            for(int i=0; i<HEADERS.length; i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            // Add Task Data
            int rowIndex = 1;
            for (Task task : tasks) {
                Row dataRow = sheet.createRow(rowIndex++);

                // Set the cell values based on the task fields
                dataRow.createCell(0).setCellValue(task.getTaskDescription());
                dataRow.createCell(1).setCellValue(task.getTaskStatus().name());  // TaskStatus is an enum
                dataRow.createCell(2).setCellValue(task.getTaskPriority().name()); // Priority is an enum
            }

            // Convert to binary data
            workbook.write(byteArrayOutputStream);

            // ByteArrayInputStream expects binary data
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
