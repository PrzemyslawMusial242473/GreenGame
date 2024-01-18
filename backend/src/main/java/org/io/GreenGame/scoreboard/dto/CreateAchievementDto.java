package org.io.GreenGame.scoreboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAchievementDto {
    String userEmail;
    String achievementName;
}
