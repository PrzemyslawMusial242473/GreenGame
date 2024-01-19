package org.io.GreenGame.friends.service.strategy;

import org.io.GreenGame.friends.model.FriendModel;

import java.util.Comparator;
import java.util.List;

public class NameLengthSortingStrategy implements SortingStrategy {
    @Override
    public List<FriendModel> apply(List<FriendModel> friends) {
        friends.sort(Comparator.comparingInt(friend -> friend.getName().length()));
        return friends;
    }
}