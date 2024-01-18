package org.io.GreenGame.scoreboard.repository;

import org.io.GreenGame.scoreboard.model.Score;
import org.io.GreenGame.user.model.GreenGameUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Score findByUser(GreenGameUser user);
    Score findByUserId(Long userId);
}
