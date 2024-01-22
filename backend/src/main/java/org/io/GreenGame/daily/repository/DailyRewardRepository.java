package org.io.GreenGame.daily.repository;

import org.io.GreenGame.daily.model.DailyReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyRewardRepository extends JpaRepository<DailyReward, Long> {
    List<DailyReward> getDailyRewardsByUserId(Long userId);

    @Query("SELECT COUNT(dailyReward.id) FROM DailyReward dailyReward WHERE dailyReward.userId = :userId")
    long countDailyRewardsByUserId(Long userId);

    @Query("SELECT dailyReward FROM DailyReward dailyReward WHERE dailyReward.userId = :userId")
    DailyReward findDailyRewardById(Long userId);
}

