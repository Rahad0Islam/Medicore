package com.medicore.communication.service;

import com.medicore.communication.dto.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    
    // Placeholder for Twilio or other SMS provider integration
    public void sendSms(String phoneNumber, String message) {
        // TODO: Integrate with Twilio API or other SMS provider
        System.out.println("SMS Service placeholder - Message to: " + phoneNumber);
        System.out.println("Message: " + message);
    }
    
    public void sendNotificationSms(NotificationRequest request) {
        sendSms(request.getRecipientPhone(), request.getMessage());
    }
}
