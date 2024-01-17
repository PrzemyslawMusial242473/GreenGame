package org.io.GreenGame.friends.repository;

import org.io.GreenGame.friends.model.FriendModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendModelRepository extends JpaRepository<FriendModel, Long> {
    Optional<FriendModel> findFriendModelById(Long id);
}
