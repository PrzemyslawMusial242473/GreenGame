package org.io.GreenGame.scoreboard.service;

import org.io.GreenGame.friends.model.FriendModel;
import org.io.GreenGame.friends.model.FriendsUserModel;
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
import java.util.Comparator;
import java.util.List;

@Service
@ComponentScan
@lombok.extern.slf4j.Slf4j
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

//    public Score getScoreByEmail(String email) {
//        GreenGameUser user = userRepository.findUserByEmail(email);
//        return scoreRepository.findByUserId(user.getId());
//    }

    public Score getScoreById(Long id) {
        return scoreRepository.findById(id).orElse(null);
    }

    public Score getScoreByUserId(Long userId) {
        return scoreRepository.findByUserId(userId);
    }

//    public Score getScoreByUser(String username) {
//        return scoreRepository.findByUsername(username);
//    }

    public int getScorePointsByUserId(Long userId) {
        return scoreRepository.findByUserId(userId).getPoints();
    }

//    public int getScorePointsByUser(String username) {
//        return scoreRepository.findByUsername(username).getPoints();
//    }

//    public int getScoreRankByUserId(Long userId) {
//        return scoreRepository.findByUserId(userId).getRank();
//    }

//    public int getScoreRankByUser(String username) {
//        return scoreRepository.findByUsername(username).getRank();
//    }

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
        return score.getPoints();
    }

    public Score addAchievementToUser(String email, String achievementName) {
        GreenGameUser user = userRepository.findUserByEmail(email);
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

    private Score addNewScore(GreenGameUser user) {
        Score score = new Score();
//        score.setRank(getLastRank() + 1);
        // int points = calculateScore(4,5,60,2);
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
    //
    public int calculateScore(int correctAnswers, int numberOfQuestions, int hp) {
        int score = 0;

        if (hp == 0) {
            return score;
        }

        double ratio = correctAnswers / (double) numberOfQuestions;
        // 99 - 33 = 66
        //33
        //0
        if (ratio == 1) {
//            score += ((correctAnswers * 10 - numberOfQuestions * 5) + 10);
            score += (ratio * 10 + (hp/33)) * 2;
            // 50 - 15
            return score;
        }

        score += (ratio * 10 + (hp/33));
        // 1 * 10
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
