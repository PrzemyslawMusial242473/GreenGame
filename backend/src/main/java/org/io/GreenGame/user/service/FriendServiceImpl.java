package org.io.GreenGame.user.service;
import jakarta.annotation.PostConstruct;
import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.model.FriendsUserModel;
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
                System.out.println("Before sorting: " + friends);
                friends = sortingStrategy.apply(friends);
                System.out.println("After sorting: " + friends);
            }

            if ("nameStartsWithUnderscore".equals(filterBy)) {
                friends = new NameFilteringStrategy().apply(friends, friend -> friend.getName().startsWith("_"));
            }

            model.setFriends(friends);
        });

        return friendsUserModel;
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

        friendRepository.save(friendsUserModel);

        FriendsUserModel friendsUserModel1 = new FriendsUserModel(2L);
        friendsUserModel1.setId(2L);
        FriendModel friendModel2 = new FriendModel(3L, "_player_");
        friendsUserModel1.addFriend(friendModel2);

        friendRepository.save(friendsUserModel1);

    }
}
