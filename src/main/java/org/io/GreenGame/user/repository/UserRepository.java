package org.io.GreenGame.user.repository;

import org.io.GreenGame.user.model.GreenGameUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GreenGameUser, Long> {

    @Query("SELECT COUNT(user.id) FROM GreenGameUser user WHERE user.id = :id")
    Long checkIfIdIsInDatabase(Long id);

    @Query("SELECT COUNT(user.Username) FROM GreenGameUser user WHERE user.Username = :username")
    Long checkIfUsernameIsInDatabase(String username);

    @Query("SELECT COUNT(user.Email) FROM GreenGameUser user WHERE user.Email = :email")
    Long checkIfEmailIsInDatabase(String email);

    @Query("SELECT user FROM GreenGameUser user WHERE user.Email = :email")
    GreenGameUser findUserByEmail(String email);
}
