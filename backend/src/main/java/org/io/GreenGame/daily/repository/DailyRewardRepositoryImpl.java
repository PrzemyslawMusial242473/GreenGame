package org.io.GreenGame.daily.repository;

import org.io.GreenGame.daily.model.DailyReward;

import java.util.ArrayList;
import java.util.List;

public class DailyRewardRepositoryImpl implements DailyRewardRepository {
    private List<DailyReward> dailyRewards = new ArrayList<>();

    @Override
    public List<DailyReward> getDailyRewardsByUserId(Long userId) {
        List<DailyReward> userDailyRewards = new ArrayList<>();
        for (DailyReward dailyReward : dailyRewards) {
            if (dailyReward.getUserId().equals(userId)) {
                userDailyRewards.add(dailyReward);
            }
        }
        return userDailyRewards;
    }

    @Override
    public void saveDailyReward(DailyReward dailyReward) {
        dailyRewards.add(dailyReward);
    }
}
