package org.io.GreenGame.scoreboard.service;

import org.io.GreenGame.friends.model.FriendModel;
import org.io.GreenGame.friends.service.FriendService;
import org.io.GreenGame.scoreboard.model.Achievement;
import org.io.GreenGame.scoreboard.model.AchievementType;
import org.io.GreenGame.scoreboard.model.Score;
import org.io.GreenGame.scoreboard.repository.AchievementRepository;
import org.io.GreenGame.scoreboard.repository.ScoreRepository;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@ComponentScan
public class ScoreboardService {
    private final ScoreRepository scoreRepository;
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;
    private final FriendService friendService;

    @Autowired
    public ScoreboardService(
            ScoreRepository scoreRepository,
            AchievementRepository achievementRepository,
            UserRepository userRepository,
            FriendService friendService) {
        this.scoreRepository = scoreRepository;
        this.achievementRepository = achievementRepository;
        this.userRepository = userRepository;
        this.friendService = friendService;
    }

    public List<Score> getAllScores() {
        List<Score> scores = scoreRepository.findAll();
        Collections.sort(scores);
        return scores;
    }

    public Score getScoreById(Long id) {
        return scoreRepository.findById(id).orElse(null);
    }

    public Score getScoreByUserId(Long userId) {
        return scoreRepository.findByUserId(userId);
    }

    public int getScorePointsByUserId(Long userId) {
        return scoreRepository.findByUserId(userId).getPoints();
    }

    public List<Score> getTopScores(int limit) {
        List<Score> scores = scoreRepository.findAll();
        Collections.sort(scores);
        try {
            return scores.subList(0, limit);
        } catch (IndexOutOfBoundsException e) {
            return scores;
        }
    }

    public List<Score> getScoresOfUserFriends(Long userId) {
        List<FriendModel> friends = friendService.getAllFriendsByOwnerId(userId).orElse(null).getFriends();
        List<Score> scores = scoreRepository.findAll();

        return scores.stream().filter(score -> {
            for (FriendModel friend : friends) {
                if (friend.getId().equals(score.getUser().getId())) {
                    return true;
                }
            }
            if (score.getUser().getId().equals(userId)) {
                return true;
            }
            return false;
        }).toList();
    }

    public List<Achievement> getAchievementsByUserId(Long userId) {
        return scoreRepository.findByUserId(userId).getAchievements();
    }

    public int getLastRank() {
        return scoreRepository.findAll().size();
    }

    public int addPointsToUser(Long userId, int points, int numberOfQuestions, int hp) {
        GreenGameUser user = userRepository.findById(userId).orElse(null);

        Score score = scoreRepository.findByUserId(userId);
        if (score == null) {
            score = addNewScore(user);
        }
        points = calculateScore(points, numberOfQuestions, hp);
        score.addPoints(points);
        score.incrementNumberOfGames();
        addAchievementsToScore(score);
        scoreRepository.save(score);
        return getRank(score);
    }

    public Score addAchievementToUser(Long userId, String achievementName) {
        GreenGameUser user = userRepository.findById(userId).orElseThrow();
        Score score = scoreRepository.findByUserId(user.getId());
        if (score == null) {
            score = addNewScore(user);
        }
        Achievement achievement = achievementRepository.findByAchievementName(achievementName);
        if (achievement == null) {
            achievement = addAchievement(achievementName);
        }
        score.addAchievement(achievement);
        scoreRepository.save(score);
        return score;
    }

    private int getRank(Score score) {
        List<Score> scores = scoreRepository.findAll();
        Collections.sort(scores);
        return scores.indexOf(score) + 1;
    }

    private Score addNewScore(GreenGameUser user) {
        Score score = new Score();
        score.setPoints(0);
        score.setUser(user);
        scoreRepository.save(score);
        return score;
    }

    public List<Achievement> getAchievements() {
        return achievementRepository.findAll();
    }

    public Achievement addAchievement(String achievementName) {
        Achievement achievement = new Achievement();
        achievement.setAchievementName(achievementName);
        achievementRepository.save(achievement);
        return achievement;
    }

    private int calculateScore(int correctAnswers, int numberOfQuestions, int hp) {
        int score = 0;

        if (hp == 0) {
            return score;
        }

        double ratio = correctAnswers / (double) numberOfQuestions;
        if (ratio == 1) {
            score += (ratio * 10 + (hp/33)) * 2;
            return score;
        }

        score += (ratio * 10 + (hp/33));
        return score;

    }

    private boolean hasAchievement(Score score, AchievementType achievementType) {
        for (Achievement achievement : score.getAchievements()) {
            if (achievement.getAchievementName().equals(achievementType.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addAchievementsToScore(Score score) {
        if (score.getPoints() >= 100) {
            addAchievementToScore(score, AchievementType.FIRST_100_POINTS);
        }

        if (score.getPoints() >= 500) {
            addAchievementToScore(score, AchievementType.FIRST_500_POINTS);
        }

        if (score.getNumberOfGames() >= 5) {
            addAchievementToScore(score, AchievementType.FIRST_5_FIGHTS);
        }

        if (score.getNumberOfGames() >= 20) {
            addAchievementToScore(score, AchievementType.FIRST_20_FIGHTS);
        }

        if (score.getPoints() >= 1) {
            addAchievementToScore(score, AchievementType.FIRST_POINTS);
        }
    }

    private void addAchievementToScore(Score score, AchievementType achievementType) {
        Achievement achievement = achievementRepository.findByAchievementName(achievementType.getName());
        if (achievement == null) {
            achievement = addAchievement(achievementType.getName());
            score.addAchievement(achievement);
            return;
        }
        if (!hasAchievement(score, achievementType)) {
            score.addAchievement(achievement);
        }
    }

}
