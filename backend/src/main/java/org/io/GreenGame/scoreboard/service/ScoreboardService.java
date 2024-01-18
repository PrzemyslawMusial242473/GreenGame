package org.io.GreenGame.scoreboard.service;

import org.io.GreenGame.scoreboard.model.Achievement;
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

    @Autowired
    public ScoreboardService(
            ScoreRepository scoreRepository,
            AchievementRepository achievementRepository,
            UserRepository userRepository) {
        this.scoreRepository = scoreRepository;
        this.achievementRepository = achievementRepository;
        this.userRepository = userRepository;
    }

    public List<Score> getAllScores() {
        List<Score> scores = scoreRepository.findAll();
        Collections.sort(scores);
        return scores;
    }

    public Score getScoreByEmail(String email) {
        GreenGameUser user = userRepository.findUserByEmail(email);
        return scoreRepository.findByUserId(user.getId());
    }

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

    public int getScoreRankByUserId(Long userId) {
        return scoreRepository.findByUserId(userId).getRank();
    }

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
        return scoreRepository.findAll();
        // TODO: implement


    }

    public List<Achievement> getAchievementsByUserId(Long userId) {
        return scoreRepository.findByUserId(userId).getAchievements();
    }

    public int getLastRank() {
        return scoreRepository.findAll().size();
    }

    public int addPointsToUser(Long userId, int points) {
//        GreenGameUser user = userRepository.findUserByEmail(userEmail);
        GreenGameUser user = userRepository.findById(userId).orElse(null);

        Score score = scoreRepository.findByUserId(userId);
        if (score == null) {
            score = addNewScore(user);
        }
        score.addPoints(points);
        score.incrementNumberOfGames();
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
        score.setRank(getLastRank() + 1);
        score.setPoints(0);
        score.setUser(user);
        scoreRepository.save(score);
        return score;
    }

    public List<Score> getScoreDesc(List<Score> scoreboard) {
        //przypadek 1 jak przekazujemy pusta listę
        Comparator <Score> comperator = Comparator.comparingInt(Score::getPoints).reversed();

        //Collections.sort(scoreboard, comperator);

        //przypadek 2 lepszy wg mnie

       scoreboard.sort(comperator);

        return scoreboard;
    }

    public List<Score> getScoreAsc(List<Score> scoreboard) {
        Comparator <Score> comperator = Comparator.comparingInt(Score::getPoints);
        scoreboard.sort(comperator);

        return scoreboard;

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
}
