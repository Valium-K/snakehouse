package dev.valium.snakehouse.module.leaderboard.dto;

import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.leaderboard.Leaderboard;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter @Setter
@Builder(access = AccessLevel.PROTECTED)
public class HistoryDTO {
    private Title title;
    private ZonedDateTime dateTime;

    public static HistoryDTO createHistoryDTO(Leaderboard leaderboard) {
        return HistoryDTO.builder()
                .title((leaderboard.getGameScore() != null) ?
                        leaderboard.getGameScore().getTitle() : null)
                .dateTime((leaderboard.getGameScore() != null) ?
                        leaderboard.getGameScore().getCreatedDate() : null)
                .build();
    }

    public static HistoryDTO createHistoryDTO(Title title, ZonedDateTime dateTime) {
        return HistoryDTO.builder()
                .title(title)
                .dateTime(dateTime)
                .build();
    }
}
