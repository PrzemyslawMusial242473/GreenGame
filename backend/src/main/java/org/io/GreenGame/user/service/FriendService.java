package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.FriendModel;

import java.util.List;
import java.util.Optional;

public interface FriendService {

    Optional<FriendModel> getFriendById(Long friendId);
    void saveFriend(Long userId, FriendModel friend);
    void updateFriend(Long friendId, FriendModel friend);
    void deleteFriend(Long friendId);
}
