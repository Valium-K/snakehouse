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
                .orElse(GameScore.createGameScore(null, 0L));
    }

    @Transactional(readOnly = true)
    public List<GameScore> findAllScoreOf(Title title, String orderBy) {

        return ("desc".equals(orderBy))
                ? gameScoreRepository.findAllByTitleOrderByScoreDesc(title)
                : gameScoreRepository.findAllByTitleOrderByScore(title);

    }

    public GameScore saveGameScore(Title title, Long score) {
        return gameScoreRepository.save(GameScore.createGameScore(title, score));
    }




}
