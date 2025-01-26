package com.example.todo_app.service.impl;

import com.example.todo_app.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.example.todo_app.constants.AppConstants.SYSTEM_MAIL;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendEmail() {
        String toEmail = "eknoorkaurwalia38@gmail.com";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SYSTEM_MAIL);
        message.setTo(toEmail);
        message.setText("");
        message.setSubject("");

        javaMailSender.send(message);
        log.info("Mail sent successfully: {}", message);
    }
}
