package org.io.GreenGame.friends.service.strategy;

import org.io.GreenGame.friends.model.FriendModel;

import java.util.List;

public interface SortingStrategy {
    List<FriendModel> apply(List<FriendModel> friends);
}
