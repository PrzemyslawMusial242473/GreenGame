package org.io.GreenGame.user.service.strategy;

import org.io.GreenGame.user.model.FriendModel;

import java.util.List;
import java.util.function.Predicate;

public interface FilteringStrategy {
    List<FriendModel> apply(List<FriendModel> friends, Predicate<FriendModel> filterCondition);
}