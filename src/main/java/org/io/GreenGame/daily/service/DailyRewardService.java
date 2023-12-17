package org.io.GreenGame.daily.service;

import org.io.GreenGame.daily.model.DailyReward;
import org.io.GreenGame.daily.repository.DailyRewardRepository;
import org.io.GreenGame.user.model.GreenGameUser;

import java.util.Date;
import java.util.List;

public class DailyRewardService {
    private DailyRewardRepository dailyRewardRepository;

    public DailyRewardService(DailyRewardRepository dailyRewardRepository) {
        this.dailyRewardRepository = dailyRewardRepository;
    }

    public void claimDailyReward(GreenGameUser user) {
        List<DailyReward> userDailyRewards = dailyRewardRepository.getDailyRewardsByUserId(user.getId());

        // Sprawdź czy dzisiejsza nagroda nie została już odebrana
        if (isDailyRewardClaimedToday(userDailyRewards)) {
            System.out.println("Dzienna nagroda została już odebrana.");
        } else {
            // Jeśli nagroda jeszcze nie została odebrana, dodaj nową nagrodę i zaktualizuj dane gracza
            DailyReward newDailyReward = new DailyReward(user.getId(), new Date(), true);
            dailyRewardRepository.saveDailyReward(newDailyReward);

            // Dodaj punkty i nagrody za codzienną aktywność
            addDailyRewards(user);

            System.out.println("Dzienna nagroda została pomyślnie odebrana.");
        }
    }

    private boolean isDailyRewardClaimedToday(List<DailyReward> userDailyRewards) {
        Date today = new Date();
        for (DailyReward dailyReward : userDailyRewards) {
            if (dailyReward.getDate().equals(today)) {
                return true;
            }
        }
        return false;
    }

    private void addDailyRewards(GreenGameUser user) {

        // Aktualizacja rankingu
        // Ranking.updateRanking(user);

        // Dodanie przedmiotu do ekwipunku
        // Ekwipunek.addItem(user, "Nowy Przedmiot");
    }
}
