package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.ChatMessage;
import org.io.GreenGame.user.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageRepository messageRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Override
    public List<ChatMessage> getChatMessages(String sender, String receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }

    @Override
    public void blockUser(String user, String contact) {
        throw new UnsupportedOperationException("Not implemented now");
    }

    @Override
    public void unblockUser(String user, String contact) {
        throw new UnsupportedOperationException("Not implemented now");
    }

    @Override
    public boolean isUserBlocked(String user, String contact) {
        throw new UnsupportedOperationException("Not implemented now");
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String user) {
        throw new UnsupportedOperationException("Not implemented now");
    }

    @Override
    public void markMessagesAsRead(String user, String sender) {
        throw new UnsupportedOperationException("Not implemented now");
    }

    @Override
    public void sendChatMessage(ChatMessage message) {
        messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/messages", message);
    }
}
