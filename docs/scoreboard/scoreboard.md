# Scoreborad

## Scoarboard - functions
- display scoreboard
- filtration by friends
- add score
- sorting
- add, delete achievement


## DB:
Stores points which a player scored in a game.
- Score
    + score_id
    + score
    + created_at
    + user_id
    + game_id

- UserAchievement
    + user_id **PK**
    + achievement_id **PK**
    + obtainment_date

- Achievement
    + achievement_id
    + name
    + logo


## Object:
- ScoreRepository
    + create
    + delete
    + get(id)
    + update
    + getScoreByUser(user)
    + getScoreByGame(game)
    + getScoreboard()
    + getScoreboardByUserFriends(user)

- ScoreService
    + createScore(user, game, score)
    + updateScore(user, game, score)
    + deleteScore(user, game, score)
    + getScoreByUser(user)
    + getScoreByGame(game)
    + getScoreboard()
    + getScoreboardByUserFriends(user)

- AchievementRepository
    + addAchievementToUser(user, achievement)
    + deleteUserAchevement(user, achievement)
    + getAllAchievements()
    + getAchievementsByUser(user)
    + getAchievementsByAchievement(achievement)


**Information for Architects: We want to communicate with users, fight and daily modules**