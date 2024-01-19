package org.io.GreenGame.scoreboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Achievement {
    @Id
    @GeneratedValue
    private Long achievementId;
    private String achievementName;
    @JsonIgnore
    @ManyToMany(mappedBy = "achievements")
    private List<Score> scores;
}
