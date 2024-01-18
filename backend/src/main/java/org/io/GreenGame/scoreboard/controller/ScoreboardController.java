package org.io.GreenGame.scoreboard.controller;

import org.io.GreenGame.scoreboard.dto.*;
import org.io.GreenGame.scoreboard.model.Achievement;
import org.io.GreenGame.scoreboard.model.Score;
import org.io.GreenGame.scoreboard.service.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/secured")
public class ScoreboardController {
    private final ScoreboardService scoreboardService;

    @Autowired
    public ScoreboardController(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    @GetMapping("/scoreboard")
    @ResponseBody
    public List<ScoreReturnDto> getScoreboard() {
        List<Score> scores = scoreboardService.getAllScores();
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
//        return scores.stream().map(score -> new ScoreReturnDto(score.getRank(),
//                score.getUser().getUsername(),
//                score.getPoints(),
//                score.getNumberOfGames(),
//                score.getAchievements().stream().map(achievement -> new AchievementDto(achievement.getAchievementName())).toList())).toList();
    }

    @PostMapping("/scoreboard")
    @ResponseBody
    public int addScore(@RequestBody ScoreReqDto scoreReqDto) {
        System.out.println(scoreReqDto.getUserEmail() + " " + scoreReqDto.getPoints());
        return scoreboardService.addPointsToUser(scoreReqDto.getUserEmail(), scoreReqDto.getPoints());
    }

    @GetMapping("/scoreboard/top/{limit}")
    @ResponseBody
    public List<ScoreReturnDto> getTopScores(@PathVariable int limit) {
        System.out.println("LIMIT: " + limit);
        List<Score> scores = scoreboardService.getTopScores(limit);
        System.out.println("TOP SCORES: " + scores.size());
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

    @GetMapping("/scoreboard/user")
    @ResponseBody
    public ScoreReturnDto getScoreByEmail(@RequestBody EmailDto email) {
        String userEmail = email.getEmail();
        System.out.println("USER EMAIL: " + userEmail);
        Score score = scoreboardService.getScoreByEmail(userEmail);
        System.out.println("SCORE: " + score.getUser().getEmail());
        return new ScoreReturnDto(score.getRank(),
                score.getUser().getUsername(),
                score.getPoints(),
                score.getNumberOfGames(),
                score.getAchievements().stream().map(achievement -> new AchievementDto(achievement.getAchievementName())).toList());
    }

    @GetMapping("/scoreboard/user/points")
    @ResponseBody
    public int getScorePointsByEmail(@RequestBody EmailDto email) {
        String userEmail = email.getEmail();
        System.out.println("USER EMAIL: " + userEmail);
        Score score = scoreboardService.getScoreByEmail(userEmail);
        System.out.println("SCORE: " + score.getUser().getEmail());
        return score.getPoints();
    }

    @GetMapping("/scoreboard/user/rank")
    @ResponseBody
    public int getScoreRankByEmail(@RequestBody EmailDto email) {
        System.out.println("USER EMAIL: " + email.getEmail());
        String userEmail = email.getEmail();
        Score score = scoreboardService.getScoreByEmail(userEmail);
        System.out.println("SCORE: " + score.getUser().getEmail());
        return score.getRank();
    }

    @GetMapping("/scoreboard/user/achievement")
    @ResponseBody
    public List<Achievement> getScoreAchievementsByEmail(@RequestBody EmailDto email) {
        String userEmail = email.getEmail();
        System.out.println("USER EMAIL: " + userEmail);
        Score score = scoreboardService.getScoreByEmail(userEmail);
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
    @ResponseBody
    public ResponseEntity addAchievement(@RequestBody AchievementDto achievementDto) {
        return ResponseEntity.ok(scoreboardService.addAchievement(achievementDto.getAchievementName()));
    }
}
