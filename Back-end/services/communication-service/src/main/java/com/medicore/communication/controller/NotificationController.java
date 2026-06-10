package com.medicore.communication.controller;

import com.medicore.communication.dto.ChatMessageDTO;
import com.medicore.communication.dto.NotificationRequest;
import com.medicore.communication.service.ChatService;
import com.medicore.communication.service.NotificationConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/communication/notification")
public class NotificationController {
    
    @Autowired
    private NotificationConsumer notificationConsumer;
    
    @Autowired
    private ChatService chatService;
    
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        notificationConsumer.processNotification(request);
        return ResponseEntity.ok("Notification sent successfully");
    }
    
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<ChatMessageDTO>> getUnreadMessages(@PathVariable Long userId) {
        List<ChatMessageDTO> messages = chatService.getUnreadMessages(userId);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/conversation/{sessionId}")
    public ResponseEntity<List<ChatMessageDTO>> getConversation(@PathVariable String sessionId) {
        List<ChatMessageDTO> messages = chatService.getConversation(sessionId);
        return ResponseEntity.ok(messages);
    }
}
