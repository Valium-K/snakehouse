package dev.valium.snakehouse.module.game.score;

import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.game.score.exception.NoSuchGameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameScoreService {

    private final GameScoreRepository gameScoreRepository;

    @Transactional(readOnly = true)
    public GameScore findHighScoreOf(Title title) {
        return gameScoreRepository.findFirstByTitleOrderByScoreDesc(title)
                .orElseThrow(() -> new NoSuchGameException(title));
    }

    @Transactional(readOnly = true)
    public List<GameScore> findAllScoreOf(Title title) {
        return gameScoreRepository.findAllByTitleOrderByScoreDesc(title);

    }

    public GameScore saveScore(Title title, Long score) {
        return gameScoreRepository.save(GameScore.createGameScore(title, score));
    }




}
