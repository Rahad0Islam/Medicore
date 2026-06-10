package com.medicore.communication.service;

import com.medicore.communication.dto.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private SmsService smsService;
    
    public void processNotification(NotificationRequest request) {
        if ("EMAIL".equalsIgnoreCase(request.getNotificationType())) {
            emailService.sendNotificationEmail(request);
        } else if ("SMS".equalsIgnoreCase(request.getNotificationType())) {
            smsService.sendNotificationSms(request);
        } else if ("BOTH".equalsIgnoreCase(request.getNotificationType())) {
            emailService.sendNotificationEmail(request);
            smsService.sendNotificationSms(request);
        }
    }
    
    // Placeholder for Kafka/RabbitMQ consumer
    // @KafkaListener(topics = "notification-topic")
    // public void consumeNotification(NotificationRequest request) {
    //     processNotification(request);
    // }
}
