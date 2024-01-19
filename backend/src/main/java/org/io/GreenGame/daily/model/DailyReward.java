package org.io.GreenGame.daily.model;

import java.util.Date;

public class DailyReward {
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
