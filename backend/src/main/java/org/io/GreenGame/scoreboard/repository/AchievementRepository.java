package org.io.GreenGame.scoreboard.repository;

import org.io.GreenGame.scoreboard.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    Achievement findByAchievementName(String achievementName);
}
