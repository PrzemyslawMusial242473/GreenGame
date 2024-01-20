package org.io.GreenGame.scoreboard.model;

public enum AchievementType {
    FIRST_POINTS("First points"),
    FIRST_100_POINTS("First 100 points"),
    FIRST_500_POINTS("First 500 points- eco freak"),
    FIRST_5_FIGHTS("First 5 fights"),
    FIRST_20_FIGHTS("First 20 fights - nolife");

    private final String name;

    AchievementType(String name) {
        this.name = name;
    }
public String getName() {
        return name;
    }

    public static AchievementType valueOfLabel(String name) {
        for (AchievementType e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return null;
    }
}
