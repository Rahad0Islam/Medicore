package com.medicore.communication.service;

import com.medicore.communication.entity.ChatMessage;
import com.medicore.communication.dto.ChatMessageDTO;
import com.medicore.communication.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    
    @Autowired
    private ChatMessageRepository messageRepository;
    
    public ChatMessage saveMessage(String sessionId, Long senderId, Long receiverId, String content) {
        ChatMessage message = new ChatMessage();
        message.setSessionId(sessionId);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setIsRead(false);
        
        return messageRepository.save(message);
    }
    
    public List<ChatMessageDTO> getConversation(String sessionId) {
        List<ChatMessage> messages = messageRepository.findBySessionId(sessionId);
        return messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ChatMessageDTO> getUnreadMessages(Long receiverId) {
        List<ChatMessage> messages = messageRepository.findByReceiverIdAndIsReadFalse(receiverId);
        return messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public void markAsRead(Long messageId) {
        messageRepository.findById(messageId).ifPresent(message -> {
            message.setIsRead(true);
            messageRepository.save(message);
        });
    }
    
    private ChatMessageDTO convertToDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setMessageId(message.getMessageId());
        dto.setSessionId(message.getSessionId());
        dto.setSenderId(message.getSenderId());
        dto.setReceiverId(message.getReceiverId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp().toString());
        dto.setIsRead(message.getIsRead());
        return dto;
    }
}
