package dev.valium.snakehouse.module.game.score;

import dev.valium.snakehouse.module.base.BaseEntity;
import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.leaderboard.Leaderboard;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameScore extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long score;

    @ApiModelProperty
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Title title;

    @OneToMany(mappedBy = "gameScore", cascade = CascadeType.REMOVE)
    private List<Leaderboard> leaderboards;

    public void setScore(Long score) {
        this.score = score;
    }
    public void setTitle(Title title) {
        this.title = title;
    }

    public static GameScore createGameScore(Title title, Long score) {
        return GameScore.builder()
                .title(title)
                .score(score)
                .build();
    }
}
