package org.io.GreenGame.user.repository;

import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.model.FriendsUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<FriendsUserModel, Long> {
   // zwrócenie wszystkich przyjaciół danego użytkownika
    Optional<FriendsUserModel> findByOwnerId(Long userId);
}