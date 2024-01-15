package org.io.GreenGame.chat.service;

import org.io.GreenGame.chat.model.ChatMessage;
import org.io.GreenGame.chat.model.ChatUserModel;
import org.io.GreenGame.chat.repository.ChatMessageRepository;
import org.io.GreenGame.chat.repository.ChatUserModelRepository;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageRepository messageRepository;
    @Autowired
    private ChatUserModelRepository chatUserModelRepository;
    @Autowired
    private AuthServiceImplementation authServiceImplementation;


    @Override
    public List<ChatMessage> getChatMessages(Long senderId, Long receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    public void blockUser(Long userId, Long userToBeBlockedId) {
        ChatUserModel user = chatUserModelRepository.findChatUserModelById(userId);
        ChatUserModel userToBeBlocked = chatUserModelRepository.findChatUserModelById(userToBeBlockedId);
        user.blockUser(userToBeBlocked);
        chatUserModelRepository.save(user);
    }

    @Override
    public void unblockUser(Long userId, Long userToBeUnblockedId) {
        ChatUserModel user = chatUserModelRepository.findChatUserModelById(userId);
        user.unblockUser(userToBeUnblockedId);
        chatUserModelRepository.save(user);
    }

    @Override
    public boolean isUserBlocked(Long userId, Long userIdToBeChecked) {
        ChatUserModel user = chatUserModelRepository.findChatUserModelById(userId);
        List<ChatUserModel> blockedUsers = user.getListOfBlockedUsers();
        for (ChatUserModel l : blockedUsers) {
            if (user.getId().equals(userIdToBeChecked)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ChatMessage> getUnreadMessages(Long user) {
        return null;
    }

    @Override
    public void markMessagesAsRead(Long senderId, Long receiverId) {

    }

    @Override
    public void sendChatMessage(ChatMessage message) {

    }
}
