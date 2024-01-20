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
import org.io.GreenGame.friends.service.FriendService;
import org.io.GreenGame.friends.service.FriendServiceImpl;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@ComponentScan
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageRepository messageRepository;
    @Autowired
    private ChatUserModelRepository chatUserModelRepository;
    @Autowired
    private FriendServiceImpl friendService;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<ChatMessage> getChatMessages(Long senderId, Long receiverId) {
        syncTables();
        return messageRepository.findMessagesBetweenUsers(senderId, receiverId);
    }

    @Override
    public boolean isUserBlocked(Long userId, Long userIdToBeChecked) {
        syncTables();
        ChatUserModel user = chatUserModelRepository.findChatUserModelById(userId);
        List<FriendModel> blockedUsers = friendService.getAllBlockedPeopleByOwnerId(userId);
        for (FriendModel l : blockedUsers) {
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
    public void markMessageAsRead(Long senderId, Long receiverId, Long messageId) {
        syncTables();
        Optional<ChatMessage> chatMessage = messageRepository.findById(messageId);
        if (chatMessage.isPresent()) {
            ChatMessage message = chatMessage.get();
            if (message.getSenderId().equals(senderId) && message.getReceiverId().equals(receiverId)) {
                message.setRead(true);
                messageRepository.save(message);
            }
        }
    }

    @Override
    public boolean sendChatMessage(ChatMessage message) {
        syncTables();
        ChatUserModel sender = chatUserModelRepository.findChatUserModelById(message.getSenderId());
        ChatUserModel receiver = chatUserModelRepository.findChatUserModelById(message.getReceiverId());

        Optional<FriendsUserModel> friends = friendService.getAllFriendsByOwnerId(message.getSenderId());

        boolean isFriend = false;
        if(friends.isPresent()) {
            List<FriendModel> friendModels = friends.get().getFriends();
            for (FriendModel friendModel : friendModels) {
                if (Objects.equals(friendModel.getId(), receiver.getId())) {
                    isFriend = true;
                    break;
                }
            }

        }
        if (!isFriend) {
            return false;
        }
        if (isUserBlocked(sender.getId(), receiver.getId())) {
            return false;
        }
        if (isUserBlocked(receiver.getId(), sender.getId())) {
            return false;
        }

        messageRepository.save(message);
        return true;

    }

    private synchronized void syncTables() {
        List<GreenGameUser> users = getAllUsersFromDatabase();
        for (GreenGameUser user : users) {
            checkIfUserExistsAndDownloadItFromDatabase(user.getId());
        }
    }

    private List<GreenGameUser> getAllUsersFromDatabase(){
        TypedQuery<GreenGameUser> query = entityManager.createQuery(
                "SELECT user FROM GreenGameUser user ORDER BY user.id ASC", GreenGameUser.class);
        List<GreenGameUser> users = query.getResultList();
        return users;
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
            System.out.println("W bazie czatu jest już użytkownik o ID: " + id);
            return true; // już jest użytkownik
        } else {
            Optional<GreenGameUser> user = findUserById(id);
            if (user.isPresent()) {
                    GreenGameUser user1 = user.get();
                    ChatUserModel chatUserModel = new ChatUserModel(user1.getId(), user1.getUsername());
                    chatUserModelRepository.save(chatUserModel);
                    System.out.println("Utworzono w bazie czatu użytkownika o ID" + id);
                    return true;
            }
            return false;
        }
    }
}
