package org.io.GreenGame.user.service;
import jakarta.annotation.PostConstruct;
import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.model.FriendsUserModel;
import org.io.GreenGame.user.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId) {
        return friendRepository.findByOwnerId(friendId);
    }

    // TODO
    @PostConstruct
    public void init() {
        FriendsUserModel friendsUserModel = new FriendsUserModel(1L);
        friendsUserModel.setId(1L);

        FriendModel friendModel = new FriendModel(2L, "gracz1423");
        friendsUserModel.addFriend(friendModel);
        friendRepository.save(friendsUserModel);
    }
}
