package org.io.GreenGame.scoreboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScoreReturnDto {
    private int rank;
    private String username;
    private int points;
    private int numberOfGames;
    private List<AchievementDto> achievements;

    public ScoreReturnDto(int rank, String username, int points, int numberOfGames, List<AchievementDto> achievements) {
        this.rank = rank;
        this.username = username;
        this.points = points;
        this.numberOfGames = numberOfGames;
        this.achievements = achievements;
    }
}
