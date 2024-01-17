package org.io.GreenGame.friends.service.strategy;

import org.io.GreenGame.friends.model.FriendModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReverseNameSortingStrategy implements SortingStrategy {
    @Override
    public List<FriendModel> apply(List<FriendModel> friends) {
        friends.sort(Collections.reverseOrder(Comparator.comparing(FriendModel::getName)));
        return friends;
    }
}