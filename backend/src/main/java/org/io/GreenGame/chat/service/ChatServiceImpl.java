package org.io.GreenGame.chat.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.io.GreenGame.chat.model.ChatMessage;
import org.io.GreenGame.chat.model.ChatUserModel;
import org.io.GreenGame.chat.repository.ChatMessageRepository;
import org.io.GreenGame.chat.repository.ChatUserModelRepository;
import org.io.GreenGame.friends.model.FriendModel;
import org.io.GreenGame.friends.model.FriendsUserModel;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageRepository messageRepository;
    @Autowired
    private ChatUserModelRepository chatUserModelRepository;
    @Autowired
    private AuthServiceImplementation authServiceImplementation;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<ChatMessage> getChatMessages(Long senderId, Long receiverId) {
        syncTables();
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    public void blockUser(Long userId, Long userToBeBlockedId) {
        syncTables();
        ChatUserModel user = chatUserModelRepository.findChatUserModelById(userId);
        ChatUserModel userToBeBlocked = chatUserModelRepository.findChatUserModelById(userToBeBlockedId);
        user.blockUser(userToBeBlocked);
        chatUserModelRepository.save(user);
    }

    @Override
    public void unblockUser(Long userId, Long userToBeUnblockedId) {
        syncTables();
        ChatUserModel user = chatUserModelRepository.findChatUserModelById(userId);
        user.unblockUser(userToBeUnblockedId);
        chatUserModelRepository.save(user);
    }

    @Override
    public boolean isUserBlocked(Long userId, Long userIdToBeChecked) {
        syncTables();
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
    public List<ChatMessage> getUnreadMessages(Long userId) {
        syncTables();
        return messageRepository.findByReceiverIdAndRead(userId, false);
    }

    @Override
    public void markMessagesAsRead(Long senderId, Long receiverId) {
        syncTables();
        // TODO implement
    }

    @Override
    public boolean sendChatMessage(ChatMessage message) {
        syncTables();
        ChatUserModel sender = chatUserModelRepository.findChatUserModelById(message.getSenderId());
        ChatUserModel receiver = chatUserModelRepository.findChatUserModelById(message.getReceiverId());

        if (isUserBlocked(sender.getId(), receiver.getId())) {
            return false;
        }
        if (isUserBlocked(receiver.getId(), sender.getId())) {
            return false;
        }

        messageRepository.save(message);
        return true;

    }

    private void syncTables() {
        List<GreenGameUser> users = authServiceImplementation.getAllUsersFromDatabase();
        for (GreenGameUser user : users) {
            checkIfUserExistsAndDownloadItFromDatabase(user.getId());
        }
    }

    public Optional<GreenGameUser> findUserById(Long id) {
        TypedQuery<GreenGameUser> query = entityManager.createQuery(
                "SELECT user FROM GreenGameUser user WHERE user.id = :id", GreenGameUser.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // zdaję sobię sprawę z tego, że jest to rozwiązanie bardzo wysoce nieefektywne. przy dużej ilości userów, sprawdzanie każdego modelu usera...
    // brr, tragiczna wydajność
    // ale ten system tego nie osiągnie, także pozwalam sobie na to w ramach implementacji.
    // z poprawnych rozwiązań podejrzewam albo lepsze cache, albo trigger na bazie danych.
    private boolean checkIfUserExistsAndDownloadItFromDatabase(Long id) {
        ChatUserModel result = chatUserModelRepository.findChatUserModelById(id);
        if (result != null) {
            return true; // już jest użytkownik
        } else {
            // Check in the list
            Optional<GreenGameUser> user = findUserById(id);
            if (user.isPresent()) {
                GreenGameUser user1 = user.get();
                ChatUserModel chatUserModel = new ChatUserModel(user1.getId(), user1.getUsername());
                chatUserModelRepository.save(chatUserModel);
                return true;
            }
            // User not found, return false
            return false;
        }
    }
}
