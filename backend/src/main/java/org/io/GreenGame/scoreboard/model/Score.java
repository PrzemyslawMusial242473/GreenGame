package org.io.GreenGame.scoreboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.io.GreenGame.user.model.GreenGameUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Score implements Comparable<Score> {
    @Id
    @GeneratedValue
    private Long scoreId;
    private int rank;
    @OneToOne()
    private GreenGameUser user;
    private int points;
    private int numberOfGames;
    @ManyToMany()
    @JoinTable(
            name = "score_achievements",
            joinColumns = @JoinColumn(name = "score_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id"))
    private List<Achievement> achievements = new ArrayList<>();

    public void addPoints(int points) {
        this.points += points;
    }

    public void addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
    }

    public void incrementNumberOfGames() {
        this.numberOfGames++;
    }

    @Override
    public int compareTo(Score o) {
        Comparator<Score> c = Comparator.comparingInt(Score::getPoints).reversed().thenComparing(Score::getNumberOfGames);
        return c.compare(this, o);
    }
}