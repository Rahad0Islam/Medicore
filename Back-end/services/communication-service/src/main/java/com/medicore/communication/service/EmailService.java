package com.medicore.communication.service;

import com.medicore.communication.dto.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired(required = false)
    private JavaMailSender javaMailSender;
    
    public void sendEmail(String recipientEmail, String subject, String message) {
        if (javaMailSender == null) {
            // Placeholder implementation
            System.out.println("Email Service not configured. Email to: " + recipientEmail);
            System.out.println("Subject: " + subject);
            System.out.println("Message: " + message);
            return;
        }
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("noreply@medicore.com");
        
        javaMailSender.send(email);
    }
    
    public void sendNotificationEmail(NotificationRequest request) {
        sendEmail(request.getRecipientEmail(), request.getSubject(), request.getMessage());
    }
}
