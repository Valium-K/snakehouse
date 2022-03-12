package dev.valium.snakehouse.module.game.score.dto;

import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.game.score.GameScore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder(access = AccessLevel.PRIVATE)
public class GameScoreDTO {
    private Title title;
    private Long score;

    public static GameScoreDTO createGameScoreDTO(Title title, Long score) {
        return GameScoreDTO.builder().title(title).score(score).build();
    }
    public static GameScoreDTO createGameScoreDTO(GameScore gameScore) {
        return GameScoreDTO.builder().title(gameScore.getTitle()).score(gameScore.getScore()).build();
    }
}
