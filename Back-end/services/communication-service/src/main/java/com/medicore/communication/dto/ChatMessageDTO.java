package com.medicore.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    
    private Long messageId;
    private String sessionId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private String timestamp;
    private Boolean isRead;
}
