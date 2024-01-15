package org.io.GreenGame.chat.service;

import org.io.GreenGame.chat.model.ChatMessage;

import java.util.List;

public interface ChatService {

    List<ChatMessage> getChatMessages(Long senderId, Long receiverId);

    void blockUser(Long userId, Long userToBeBlockedId);

    void unblockUser(Long userId, Long userToBeUnblocked);

    boolean isUserBlocked(Long userId, Long userIdToBeChecked);

    List<ChatMessage> getUnreadMessages(Long user);

    void markMessagesAsRead(Long senderId, Long receiverId);

    public void sendChatMessage(ChatMessage message);
}
