package org.io.GreenGame.user.repository;

import org.io.GreenGame.user.model.ChatUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserModelRepository extends JpaRepository<ChatUserModel, Long> {
}
