package dev.valium.snakehouse.module.leaderboard;

import dev.valium.snakehouse.module.base.BaseEntity;
import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.game.score.GameScore;
import dev.valium.snakehouse.module.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Leaderboard extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leaderboard_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_fk")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "game_score_fk")
    private GameScore gameScore;

    public static Leaderboard createLeaderboard(Member member, GameScore gameScore) {
        return Leaderboard.builder()
                .member(member)
                .gameScore(gameScore)
                .build();
    }
}
