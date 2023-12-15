package org.io.GreenGame.user.service;
import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService{
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public Optional<FriendModel> getFriendById(Long friendId) {
        return friendRepository.findById(friendId);
    }

    @Override
    public void saveFriend(Long userId, FriendModel friend) {
        friend.setId(userId);
        friendRepository.save(friend);
    }

    @Override
    public void updateFriend(Long friendId, FriendModel friend) {
        friend.setId(friendId);
        friendRepository.save(friend);
    }

    @Override
    public void deleteFriend(Long friendId) {
        friendRepository.deleteById(friendId);
    }
}
