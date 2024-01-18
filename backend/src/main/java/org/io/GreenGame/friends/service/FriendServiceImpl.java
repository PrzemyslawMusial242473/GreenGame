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

    @Override
    public List<FriendModel> getAllBlockedPeopleByOwnerId(Long friendId) {
        syncTables();
        Optional<FriendsUserModel> friendsUserModel = friendRepository.findByOwnerId(friendId);

        if (friendsUserModel.isPresent()) {
            return friendsUserModel.get().getBlockedUsers();
        } else {
            return new ArrayList<>();
        }
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

    public void addFriend(Long friendId, Long userId) {
        syncTables();
        Optional<FriendsUserModel> friendsUserModelOptional = friendRepository.findByOwnerId(userId);

        friendsUserModelOptional.ifPresent(model -> {
            List<FriendModel> friends = model.getFriends();

            friends.removeIf(friend -> friend.getId().equals(friendId));

            model.setFriends(friends);
            friendRepository.save(model);
        });
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
         /*
         FriendsUserModel friendsUserModel = new FriendsUserModel(1L);
        friendsUserModel.setId(1L);

        FriendModel friendModel = new FriendModel(2L, "gracz1423");
        FriendModel friendModel = new FriendModel(1L, "gracz1423");
        friendsUserModel.addFriend(friendModel);

        FriendModel friendModel1 = new FriendModel(2L, "poke");
        friendsUserModel.addFriend(friendModel1);

        friendRepository.save(friendsUserModel);

        FriendsUserModel friendsUserModel1 = new FriendsUserModel(2L);
        friendsUserModel1.setId(2L);
        FriendModel friendModel2 = new FriendModel(3L, "_player_");
        friendsUserModel1.addFriend(friendModel2);

        friendRepository.save(friendsUserModel1);
        */
        System.out.println("Invitation sender ID: " + invitation.getSenderId());
        System.out.println("Invitation recipient ID: " + invitation.getRecipientId());
        Optional<FriendsUserModel> user1 = friendRepository.findByOwnerId(invitation.getSenderId());
        System.out.println("Does it reach after user1 findByOwnderId");

        user1.ifPresent(model -> {
            System.out.println("Before Optional<FriendModel>");
            Optional<FriendModel> addedOptional = friendModelRepository.findFriendModelById(invitation.getRecipientId());
            System.out.println("It reached after Optional<FriendModel>");
            addedOptional.ifPresent(added -> {
                model.addFriend(added);
                friendRepository.save(model);
            });
        });

        Optional<FriendsUserModel> user2 = friendRepository.findByOwnerId(invitation.getRecipientId());
        user2.ifPresent(model -> {
            Optional<FriendModel> addedOptional = friendModelRepository.findFriendModelById(invitation.getSenderId());
            addedOptional.ifPresent(added -> {
                model.addFriend(added);
                friendRepository.save(model);
            });
        });
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
    public void unblockGamePlayer(Long userId, Long blockedId) {
        syncTables();
        Optional<FriendsUserModel> friendsUserModelOptional = friendRepository.findByOwnerId(userId);
        Optional<FriendModel> friendModelOptional = friendModelRepository.findFriendModelById(blockedId);

        friendsUserModelOptional.ifPresent(model -> {
            friendModelOptional.ifPresent(model::unblockUser);
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
        List<GreenGameUser> users = getAllUsersFromDatabase();
        System.out.println("Size of users");
        System.out.println(users.size());
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

    private boolean checkIfUserExistsAndDownloadItFromDatabase(Long id) {
        boolean existsInFriendModel = friendModelRepository.findFriendModelById(id).isPresent();
        boolean existsInFriendUserModel = friendRepository.findByOwnerId(id).isPresent(); // Assuming a method to find by user ID

        if (existsInFriendModel && existsInFriendUserModel) {
            return true; // User already exists in both tables
        } else {
            Optional<GreenGameUser> userOptional = findUserById(id);
            if (userOptional.isPresent()) {
                GreenGameUser user = userOptional.get();
                if (!existsInFriendModel) {
                    friendModelRepository.save(new FriendModel(user.getId(), user.getUsername()));
                }
                if (!existsInFriendUserModel) {
                    FriendsUserModel friendUserModel = new FriendsUserModel(user.getId());
                    friendRepository.save(friendUserModel); // Assuming a save method in the repository
                }
                return true;
            }
            return false; // User not found in main table, abort
        }
    }

    @Override
    public List<FriendModel> getAllUsersOfService() {
        TypedQuery<GreenGameUser> query = entityManager.createQuery(
                "SELECT user FROM GreenGameUser user ORDER BY user.id ASC", GreenGameUser.class);
        List<GreenGameUser> users = query.getResultList();

        List<FriendModel> usersReturned = new ArrayList<>();
        for (GreenGameUser user : users) {
            usersReturned.add(new FriendModel(user.getId(), user.getUsername()));
        }
        return usersReturned;
    }

    private List<GreenGameUser> getAllUsersFromDatabase(){
        TypedQuery<GreenGameUser> query = entityManager.createQuery(
                "SELECT user FROM GreenGameUser user ORDER BY user.id ASC", GreenGameUser.class);
        List<GreenGameUser> users = query.getResultList();
        return users;
    }
}
