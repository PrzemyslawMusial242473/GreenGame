package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.ChatMessage;

import java.util.List;

public interface ChatService {

    List<ChatMessage> getChatMessages(String sender, String receiver);

    void blockUser(String user, String contact);

    void unblockUser(String user, String contact);

    boolean isUserBlocked(String user, String contact);

    List<ChatMessage> getUnreadMessages(String user);

    void markMessagesAsRead(String user, String sender);

    public void sendChatMessage(ChatMessage message);

    // Add more methods as needed for your specific requirements
}
