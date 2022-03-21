package dev.valium.snakehouse.module.game.score;

import dev.valium.snakehouse.module.common.PageRequestProvider;
import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.game.score.exception.NoSuchGameException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.valium.snakehouse.infra.spring.SpringConfig.SLICE_SIZE;

@Service
@Transactional
@RequiredArgsConstructor
public class GameScoreService {

    private final GameScoreRepository gameScoreRepository;

    @Transactional(readOnly = true)
    public GameScore findHighScoreOf(Title title) {
        return gameScoreRepository.findFirstByTitleOrderByScoreDescCreatedDateAsc(title)
                .orElse(GameScore.createGameScore(null, 0L));
    }

    @Transactional(readOnly = true)
    public List<GameScore> findAllScoreOf(Title title, String orderBy, Integer page) {

        // 전체조회
        if (page == null)
            return ("desc".equals(orderBy))
                ? gameScoreRepository.findAllByTitleOrderByScoreDesc(title)
                : gameScoreRepository.findAllByTitleOrderByScore(title);

        // 페이징
        PageRequest pageRequest = PageRequestProvider.get(orderBy, page, "createdDate");

        return gameScoreRepository.findAllByTitle(title, pageRequest).getContent();
    }

    public GameScore saveGameScore(Title title, Long score) {
        return gameScoreRepository.save(GameScore.createGameScore(title, score));
    }




}
