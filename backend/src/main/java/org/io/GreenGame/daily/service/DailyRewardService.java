package org.io.GreenGame.daily.service;

import org.io.GreenGame.daily.model.DailyReward;
import org.io.GreenGame.user.model.GreenGameUser;

import java.util.List;

public interface DailyRewardService {
    void claimDailyReward(Long userID);
    boolean isDailyRewardClaimedToday(List<DailyReward> userDailyRewards);
    void addDailyRewards(Long userID);
}
