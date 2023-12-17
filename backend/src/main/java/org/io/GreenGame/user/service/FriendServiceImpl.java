package org.io.GreenGame.user.service;
import jakarta.annotation.PostConstruct;
import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.model.FriendsUserModel;
import org.io.GreenGame.user.model.Invitation;
import org.io.GreenGame.user.repository.FriendRepository;
import org.io.GreenGame.user.service.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;
    private SortingStrategy sortingStrategy;
    private FilteringStrategy filteringStrategy;
    private List<FriendInvitationObserver> observers;

    @Override
    public Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId) {
        return friendRepository.findByOwnerId(friendId);
    }

    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    @Override
    public Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId, String sortBy, String filterBy) {
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
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public void sendFriendRequest(Long senderId, Long recipientId) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public void acceptFriendRequest(Long invitationId) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public void declineFriendRequest(Long invitationId) {
        throw new UnsupportedOperationException("Method not implemented yet");
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
        FriendsUserModel friendsUserModel = new FriendsUserModel(1L);
        friendsUserModel.setId(1L);

        FriendModel friendModel = new FriendModel(1L, "gracz1423");
        friendsUserModel.addFriend(friendModel);

        FriendModel friendModel1 = new FriendModel(2L, "poke");
        friendsUserModel.addFriend(friendModel1);

        FriendModel friendModel3 = new FriendModel(3L, "abcniemampomyslunanick");
        friendsUserModel.addFriend(friendModel3);

        friendRepository.save(friendsUserModel);

        FriendsUserModel friendsUserModel1 = new FriendsUserModel(2L);
        friendsUserModel1.setId(2L);
        FriendModel friendModel2 = new FriendModel(4L, "_player_");
        friendsUserModel1.addFriend(friendModel2);

        friendRepository.save(friendsUserModel1);

    }
}
