package org.io.GreenGame.daily.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="dailyrewards")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DailyReward {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Date date;
    private boolean completed;

    public DailyReward(Long userId, Date date, boolean completed) {
        this.userId = userId;
        this.date = date;
        this.completed = completed;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
