package org.io.GreenGame.daily.repository;

import org.io.GreenGame.daily.model.DailyReward;

import java.util.List;

public interface DailyRewardRepository {
    List<DailyReward> getDailyRewardsByUserId(Long userId);
    void saveDailyReward(DailyReward dailyReward);
}
