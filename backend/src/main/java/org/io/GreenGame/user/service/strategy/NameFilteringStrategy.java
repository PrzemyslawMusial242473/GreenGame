package org.io.GreenGame.user.service.strategy;

import org.io.GreenGame.user.model.FriendModel;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NameFilteringStrategy implements FilteringStrategy {
    @Override
    public List<FriendModel> apply(List<FriendModel> friends, Predicate<FriendModel> filterCondition) {
        return friends.stream()
                .filter(filterCondition)
                .collect(Collectors.toList());
    }
}