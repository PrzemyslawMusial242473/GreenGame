package org.io.GreenGame.user.service.strategy;

import org.io.GreenGame.user.model.FriendModel;

import java.util.List;

public interface SortingStrategy {
    List<FriendModel> apply(List<FriendModel> friends);
}
