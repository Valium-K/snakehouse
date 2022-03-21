package dev.valium.snakehouse.module.game.score;

import dev.valium.snakehouse.module.game.Title;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {

    Optional<GameScore> findFirstByTitleOrderByScoreDescCreatedDateAsc(Title title);
    List<GameScore> findAllByTitleOrderByScoreDesc(Title title);
    List<GameScore> findAllByTitleOrderByScore(Title title);
    Slice<GameScore> findAllByTitle(Title title, Pageable pageable);
}
