package com.medicore.communication.repository;

import com.medicore.communication.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    List<ChatMessage> findBySessionId(String sessionId);
    
    List<ChatMessage> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    
    List<ChatMessage> findByReceiverIdAndIsReadFalse(Long receiverId);
}
