package org.io.GreenGame.friends.repository;

import org.io.GreenGame.friends.model.FriendsUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<FriendsUserModel, Long> {
   // zwrócenie wszystkich przyjaciół danego użytkownika
    Optional<FriendsUserModel> findByOwnerId(Long userId);
}