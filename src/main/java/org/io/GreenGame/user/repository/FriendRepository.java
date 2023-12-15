package org.io.GreenGame.user.repository;

import org.io.GreenGame.user.model.FriendModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<FriendModel, Long> {
    Optional<FriendModel> findById(Long userId);
}