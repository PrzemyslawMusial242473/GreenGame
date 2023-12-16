package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.model.FriendsUserModel;

import java.util.List;
import java.util.Optional;

public interface FriendService {

    Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId);

}
