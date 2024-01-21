package org.io.GreenGame.chat.repository;

import org.io.GreenGame.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<ChatMessage> findByReceiverIdAndRead(Long receiverId, boolean isRead);
    @Query("SELECT m FROM ChatMessage m WHERE (m.senderId = :senderId AND m.receiverId = :receiverId) OR (m.senderId = :receiverId AND m.receiverId = :senderId) ORDER BY m.timestamp ASC")
    List<ChatMessage> findMessagesBetweenUsers(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}