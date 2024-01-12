package org.io.GreenGame.friends.service;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.io.GreenGame.friends.model.FriendModel;
import org.io.GreenGame.friends.model.FriendsUserModel;
import org.io.GreenGame.friends.model.Invitation;
import org.io.GreenGame.friends.model.InvitationStatus;
import org.io.GreenGame.friends.repository.FriendModelRepository;
import org.io.GreenGame.friends.repository.FriendRepository;
import org.io.GreenGame.friends.repository.InvitationRepository;
import org.io.GreenGame.friends.service.strategy.*;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ComponentScan
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private FriendModelRepository friendModelRepository;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private AuthServiceImplementation authServiceImplementation;
    @PersistenceContext
    private EntityManager entityManager;
    private SortingStrategy sortingStrategy;
    private FilteringStrategy filteringStrategy;
    private List<FriendInvitationObserver> observers;


    @Override
    public Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId) {
        syncTables();
        return friendRepository.findByOwnerId(friendId);
    }

    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    @Override
    public Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId, String sortBy, String filterBy) {
        syncTables();
        Optional<FriendsUserModel> friendsUserModel = friendRepository.findByOwnerId(friendId);

        friendsUserModel.ifPresent(model -> {
            List<FriendModel> friends = model.getFriends();

            if ("name".equals(sortBy)) {
                sortingStrategy = new NameSortingStrategy();
            } else if ("reverseName".equals(sortBy)) {
                sortingStrategy = new ReverseNameSortingStrategy();
            } else if ("nameLength".equals(sortBy)) {
                sortingStrategy = new NameLengthSortingStrategy();
            }

            if (sortingStrategy != null) {
                friends = sortingStrategy.apply(friends);
            }

            if ("nameStartsWithUnderscore".equals(filterBy)) {
                filteringStrategy = new NameFilteringStrategy();
                friends = filteringStrategy.apply(friends, friend -> friend.getName().startsWith("_"));
            }

            model.setFriends(friends);
        });

        return friendsUserModel;
    }

    @Override
    public void removeFriend(Long friendId, Long userId) {
        syncTables();
        Optional<FriendsUserModel> friendsUserModelOptional = friendRepository.findByOwnerId(userId);

        friendsUserModelOptional.ifPresent(model -> {
            List<FriendModel> friends = model.getFriends();

            friends.removeIf(friend -> friend.getId().equals(friendId));

            model.setFriends(friends);
            friendRepository.save(model);
        });
    }

    // TODO
    @Override
    public List<Invitation> getPendingInvitations(Long userId) {
        syncTables();
        return invitationRepository.findByRecipientIdAndStatus(userId, InvitationStatus.PENDING);
    }

    @Override
    public void sendFriendRequest(Long senderId, Long recipientId) {
        syncTables();
        Invitation invitation = new Invitation(senderId, recipientId, InvitationStatus.PENDING);
        invitationRepository.save(invitation);
        //notifyObservers(senderId);
    }

    @Override
    public void acceptFriendRequest(Long invitationId) throws ChangeSetPersister.NotFoundException {
        syncTables();
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);
        //notifyObservers(userId);
    }

    @Override
    public void declineFriendRequest(Long invitationId) throws ChangeSetPersister.NotFoundException {
        syncTables();
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        invitation.setStatus(InvitationStatus.DECLINED);
        invitationRepository.save(invitation);
        //notifyObservers(userId);
    }

    @Override
    public void blockGamePlayer(Long userId, Long blockedId) {
        syncTables();
        Optional<FriendsUserModel> friendsUserModelOptional = friendRepository.findByOwnerId(userId);
        Optional<FriendModel> friendModelOptional = friendModelRepository.findFriendModelById(blockedId);

        friendsUserModelOptional.ifPresent(model -> {
            List<FriendModel> friends = model.getFriends();
            friends.removeIf(friend -> friend.getId().equals(blockedId));
            model.setFriends(friends);

            friendModelOptional.ifPresent(model::blockUser);
            friendRepository.save(model);
        });
    }

    @Override
    public void addObserver(Long userId, FriendInvitationObserver observer) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public void removeObserver(Long userId, FriendInvitationObserver observer) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public void notifyObservers(Long userId) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    // TODO
    @PostConstruct
    public void init() {
        syncTables();
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

    @Override
    public List<FriendModel> getAllUsersOfService() {
        List<GreenGameUser> users = authServiceImplementation.getAllUsersFromDatabase();
        List<FriendModel> users_returned = new ArrayList<>();
        for (GreenGameUser user : users){
            users_returned.add(new FriendModel(user.getId(), user.getUsername()));
        }
        return users_returned;
    }

    // zdaję sobię sprawę z tego, że jest to rozwiązanie bardzo wysoce nieefektywne. przy dużej ilości userów, sprawdzanie każdego modelu usera...
    // brr, tragiczna wydajność
    // ale ten system tego nie osiągnie, także pozwalam sobie na to w ramach implementacji.
    // z poprawnych rozwiązań podejrzewam albo lepsze cache, albo trigger na bazie danych.
    private boolean checkIfUserExistsAndDownloadItFromDatabase(Long id) {
        Optional<FriendModel> result = friendModelRepository.findFriendModelById(id);
        if (result.isPresent()) {
            return true; // już jest użytkownik
        } else {
            // sprawdzmy w liscie
            Optional<GreenGameUser> userOptional = findUserById(id);
            if (userOptional.isPresent()) {
                GreenGameUser user = userOptional.get();
                friendModelRepository.save(new FriendModel(user.getId(), user.getUsername()));
                FriendsUserModel friendsUserModel = new FriendsUserModel(user.getId());
                friendsUserModel.setId(user.getId());
                friendRepository.save(friendsUserModel);
                return true;
            }
            // widac nie ma, abort
            return false;
        }
    }
}
