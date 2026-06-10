package com.medicore.communication.websocket;

import com.medicore.communication.dto.ChatMessageDTO;
import com.medicore.communication.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO message) {
        chatService.saveMessage(
                message.getSessionId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getContent()
        );
        return message;
    }
    
    @MessageMapping("/chat.markRead")
    public void markMessageAsRead(@Payload Long messageId) {
        chatService.markAsRead(messageId);
    }
}
