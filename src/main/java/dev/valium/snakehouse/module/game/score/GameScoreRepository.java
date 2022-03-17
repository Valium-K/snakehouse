package dev.valium.snakehouse.module.game.score;

import dev.valium.snakehouse.module.game.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {

    Optional<GameScore> findFirstByTitleOrderByScoreDesc(Title title);
    List<GameScore> findAllByTitleOrderByScoreDesc(Title title);
    List<GameScore> findAllByTitleOrderByScore(Title title);
}
