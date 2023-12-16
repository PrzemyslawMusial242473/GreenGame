package org.io.GreenGame.user.service;
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
}
