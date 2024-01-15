package org.io.GreenGame.chat.repository;

import org.io.GreenGame.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
