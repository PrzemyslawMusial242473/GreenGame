package org.io.GreenGame.scoreboard.controller;

import org.io.GreenGame.scoreboard.dto.*;
import org.io.GreenGame.scoreboard.model.Achievement;
import org.io.GreenGame.scoreboard.model.Score;
import org.io.GreenGame.scoreboard.service.ScoreboardService;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/secured")
public class ScoreboardController {
    private final ScoreboardService scoreboardService;
    private final AuthServiceImplementation authServiceImplementation;

    @Autowired
    public ScoreboardController(ScoreboardService scoreboardService,
                                AuthServiceImplementation authServiceImplementation) {
        this.scoreboardService = scoreboardService;
        this.authServiceImplementation = authServiceImplementation;
    }

    @GetMapping("/scoreboard")
    @ResponseBody
    public List<ScoreReturnDto> getScoreboard() {
        List<Score> scores = scoreboardService.getAllScores();
        return mapToScoreReturnDto(scores);
//        List<ScoreReturnDto> returnDtos = new ArrayList<>();
//        for (int i = 1; i <= scores.size(); i++) {
//            returnDtos.add(new ScoreReturnDto(i,
//                    scores.get(i - 1).getUser().getUsername(),
//                    scores.get(i - 1).getPoints(),
//                    scores.get(i - 1).getNumberOfGames(),
//                    scores.get(i - 1).getAchievements()
//                            .stream()
//                            .map(achievement -> new AchievementDto(achievement.getAchievementName())).toList()));
//        }
//        return returnDtos;
//        return scores.stream().map(score -> new ScoreReturnDto(score.getRank(),
//                score.getUser().getUsername(),
//                score.getPoints(),
//                score.getNumberOfGames(),
//                score.getAchievements().stream().map(achievement -> new AchievementDto(achievement.getAchievementName())).toList())).toList();
    }

    @PostMapping("/scoreboard")
    @ResponseBody
    public ResponseEntity<Integer> addScore(@RequestBody ScoreReqDto scoreReqDto) {
        try {
            int points = scoreboardService.addPointsToUser(getIdOfLoggedUser(),
                    scoreReqDto.getPoints(),
                    scoreReqDto.getNumberOfQuestions(),
                    scoreReqDto.getHp());
            return ResponseEntity.ok(points);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/scoreboard/top/{limit}")
    @ResponseBody
    public List<ScoreReturnDto> getTopScores(@PathVariable int limit) {
        System.out.println("LIMIT: " + limit);
        List<Score> scores = scoreboardService.getTopScores(limit);
        System.out.println("TOP SCORES: " + scores.size());
        return mapToScoreReturnDto(scores);
//        List<ScoreReturnDto> returnDtos = new ArrayList<>();
//        for (int i = 1; i <= scores.size(); i++) {
//            returnDtos.add(new ScoreReturnDto(i,
//                    scores.get(i - 1).getUser().getUsername(),
//                    scores.get(i - 1).getPoints(),
//                    scores.get(i - 1).getNumberOfGames(),
//                    scores.get(i - 1).getAchievements()
//                            .stream()
//                            .map(achievement -> new AchievementDto(achievement.getAchievementName())).toList()));
//        }
//        return returnDtos;
    }

    @GetMapping("/scoreboard/friends")
    @ResponseBody
    public List<ScoreReturnDto> getScoresOfUserFriends() {
        List<Score> scores = scoreboardService.getScoresOfUserFriends(getIdOfLoggedUser());
        GreenGameUser user = authServiceImplementation.getUserFromSession();
        System.out.println("User: " + user.getEmail());
        Score score = scoreboardService.getScoreByUserId(getIdOfLoggedUser());
        System.out.println("Score: " + score.getPoints());

        return mapToScoreReturnDto(scores);
//        List<ScoreReturnDto> returnDtos = new ArrayList<>();
//        for (int i = 1; i <= scores.size(); i++) {
//            returnDtos.add(new ScoreReturnDto(i,
//                    scores.get(i - 1).getUser().getUsername(),
//                    scores.get(i - 1).getPoints(),
//                    scores.get(i - 1).getNumberOfGames(),
//                    scores.get(i - 1).getAchievements()
//                            .stream()
//                            .map(achievement -> new AchievementDto(achievement.getAchievementName())).toList()));
//        }
//        return returnDtos;
    }

    @GetMapping("/scoreboard/user")
    @ResponseBody
    public ScoreReturnDto getScoreByUser() {
//        GreenGameUser user = authServiceImplementation.getUserFromSession();
//        System.out.println("User: " + user.getEmail());
//        Score score = scoreboardService.getScoreByEmail(user.getEmail());
//        System.out.println("Score: " + score.getPoints());
        Score score = scoreboardService.getScoreByUserId(getIdOfLoggedUser());
        return new ScoreReturnDto(0,
                score.getUser().getUsername(),
                score.getPoints(),
                score.getNumberOfGames(),
                score.getAchievements().stream().map(achievement -> new AchievementDto(achievement.getAchievementName())).toList());
    }

    @GetMapping("/scoreboard/user/points")
    @ResponseBody
    public int getScorePointsByUser() {
        return scoreboardService.getScorePointsByUserId(getIdOfLoggedUser());
    }

//    @GetMapping("/scoreboard/user")
//    @ResponseBody
//    public ScoreReturnDto getScoreByEmail(@RequestBody EmailDto email) {
//        String userEmail = email.getEmail();
//        System.out.println("USER EMAIL: " + userEmail);
//        Score score = scoreboardService.getScoreByEmail(userEmail);
//        System.out.println("SCORE: " + score.getUser().getEmail());
//        return new ScoreReturnDto(score.getRank(),
//                score.getUser().getUsername(),
//                score.getPoints(),
//                score.getNumberOfGames(),
//                score.getAchievements().stream().map(achievement -> new AchievementDto(achievement.getAchievementName())).toList());
//    }

//    @GetMapping("/scoreboard/user/points")
//    @ResponseBody
//    public int getScorePointsByEmail(@RequestBody EmailDto email) {
//        String userEmail = email.getEmail();
//        System.out.println("USER EMAIL: " + userEmail);
//        Score score = scoreboardService.getScoreByEmail(userEmail);
//        System.out.println("SCORE: " + score.getUser().getEmail());
//        return score.getPoints();
//    }
//
//    @GetMapping("/scoreboard/user/rank")
//    @ResponseBody
//    public int getScoreRankByEmail(@RequestBody EmailDto email) {
//        System.out.println("USER EMAIL: " + email.getEmail());
//        String userEmail = email.getEmail();
//        Score score = scoreboardService.getScoreByEmail(userEmail);
//        System.out.println("SCORE: " + score.getUser().getEmail());
//        return score.getRank();
//    }

    @GetMapping("/scoreboard/user/achievement")
    @ResponseBody
    public List<Achievement> getUserAchievements() {
//        String userEmail = email.getEmail();
//        System.out.println("USER EMAIL: " + userEmail);
//        Score score = scoreboardService.getScoreByEmail(userEmail);
        Score score = scoreboardService.getScoreByUserId(getIdOfLoggedUser());
        System.out.println("SCORE: " + score.getUser().getEmail());
        return score.getAchievements();
    }

    @PostMapping("/scoreboard/user/achievement")
    @ResponseBody
    public Score addScoreAchievementsByEmail(@RequestBody CreateAchievementDto achievementDto) {
        String userEmail = achievementDto.getUserEmail();
        System.out.println("USER EMAIL: " + userEmail);
        System.out.println("achievement name: " + achievementDto.getAchievementName());
        return scoreboardService.addAchievementToUser(userEmail, achievementDto.getAchievementName());
    }

    @GetMapping("/scoreboard/achievement")
    @ResponseBody
    public List<Achievement> getAchievements() {
        return scoreboardService.getAchievements();
    }

    @PostMapping("/scoreboard/achievement")
//    @ResponseBody
    public ResponseEntity addAchievement(@RequestBody AchievementDto achievementDto) {
        return ResponseEntity.ok(scoreboardService.addAchievement(achievementDto.getAchievementName()));
    }

    private Long getIdOfLoggedUser(){
        return authServiceImplementation.getUserFromSession().getId();
    }

    private List<ScoreReturnDto> mapToScoreReturnDto(List<Score> scores) {
//        List<ScoreReturnDto> returnDtos = new ArrayList<>();
//        for (Score score : scores) {
//            returnDtos.add(new ScoreReturnDto(
//                    score.getUser().getUsername(),
//                    score.getPoints(),
//                    score.getNumberOfGames(),
//                    score.getAchievements()
//                            .stream()
//                            .map(achievement -> new AchievementDto(achievement.getAchievementName())).toList()));
//        }
//        return returnDtos;
        List<ScoreReturnDto> returnDtos = new ArrayList<>();
        for (int i = 1; i <= scores.size(); i++) {
            returnDtos.add(new ScoreReturnDto(i,
                    scores.get(i - 1).getUser().getUsername(),
                    scores.get(i - 1).getPoints(),
                    scores.get(i - 1).getNumberOfGames(),
                    scores.get(i - 1).getAchievements()
                            .stream()
                            .map(achievement -> new AchievementDto(achievement.getAchievementName())).toList()));
        }
        return returnDtos;
    }
}
