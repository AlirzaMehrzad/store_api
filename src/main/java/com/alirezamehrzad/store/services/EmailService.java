package com.alirezamehrzad.store.services;

import com.alirezamehrzad.store.config.RabbitConfig;
import com.alirezamehrzad.store.dtos.EmailMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
    public void consumeAndSendEmail(EmailMessageDto emailMessage) {
        System.out.println("Sending email to: " + emailMessage.to());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com"); // Still hardcoded to your sending address
        message.setTo(emailMessage.to());
        message.setSubject(emailMessage.subject());
        message.setText(emailMessage.body());

        mailSender.send(message);
    }
}