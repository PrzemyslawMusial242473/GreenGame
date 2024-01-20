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
    @PersistenceContext
    private EntityManager entityManager;
    private SortingStrategy sortingStrategy;
    private FilteringStrategy filteringStrategy;


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

    @Override
    public List<Invitation> getPendingInvitations(Long userId) {
        syncTables();
        return invitationRepository.findByRecipientIdAndStatus(userId, InvitationStatus.PENDING);
    }

    @Override
    public boolean sendFriendRequest(Long senderId, Long recipientId) {
        syncTables();
        List<Invitation> currentInvs = invitationRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if ((currentInvs.size()) <= 1 && (senderId != recipientId)) {
            Invitation invitation = new Invitation(senderId, recipientId, InvitationStatus.PENDING);
            invitationRepository.save(invitation);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void acceptFriendRequest(Long invitationId) throws ChangeSetPersister.NotFoundException {
        syncTables();
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);
        Optional<FriendsUserModel> user1 = friendRepository.findByOwnerId(invitation.getSenderId());

        user1.ifPresent(model -> {
            Optional<FriendModel> addedOptional = friendModelRepository.findFriendModelById(invitation.getRecipientId());
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

    @PostConstruct
    public void init() {
        syncTables();
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

    private boolean checkIfUserExistsAndDownloadItFromDatabase(Long id) {
        boolean existsInFriendModel = friendModelRepository.findFriendModelById(id).isPresent();
        boolean existsInFriendUserModel = friendRepository.findByOwnerId(id).isPresent();

        if (existsInFriendModel && existsInFriendUserModel) {
            return true;
        } else {
            Optional<GreenGameUser> userOptional = findUserById(id);
            if (userOptional.isPresent()) {
                GreenGameUser user = userOptional.get();
                if (!existsInFriendModel) {
                    friendModelRepository.save(new FriendModel(user.getId(), user.getUsername()));
                }
                if (!existsInFriendUserModel) {
                    FriendsUserModel friendUserModel = new FriendsUserModel(user.getId());
                    friendRepository.save(friendUserModel);
                }
                return true;
            }
            return false;
        }
    }
}
