package org.io.GreenGame.chat.repository;

import org.io.GreenGame.chat.model.ChatUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserModelRepository extends JpaRepository<ChatUserModel, Long> {
    ChatUserModel findChatUserModelById(Long id);
}
