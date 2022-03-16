package dev.valium.snakehouse.module.api.v1.game.score;

import dev.valium.snakehouse.module.api.model.response.ListResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.model.response.SingleResult;
import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.game.score.GameScore;
import dev.valium.snakehouse.module.game.score.GameScoreService;
import dev.valium.snakehouse.module.game.score.dto.GameScoreDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"2. GameScore"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class GameScoreController {

    private final GameScoreService gameScoreService;
    private final ResponseService responseService;

    /********************************** 조회 ***************************************/
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "전체 게임 조회", notes = "모든 게임 정보를 조회한다.")
    @GetMapping(value = "/game")
    public ListResult<Title> findAllGame() {
        return responseService.getListResult(List.of(Title.values()));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게임 최고점수 조회", notes = "해당 게임의 최고점수를 조회한다.")
    @GetMapping(value = "/game/{game-name}/high-score")
    public SingleResult<Long> findHighScore(
            @ApiParam(value = "게임 타이틀", required = true) @PathVariable(name = "game-name") Title title) {

        Title.checkTitle(title);

        GameScore gameScore = gameScoreService.findHighScoreOf(title);
        return responseService.getSingleResult(gameScore.getScore());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게임 전체 점수 조회", notes = "해당 게임의 전체 점수를 내림차순으로 조회한다.")
    @GetMapping(value = "/game/{game-name}/score")
    public ListResult<Long> findAllScore(
            @ApiParam(value = "게임 타이틀", required = true) @PathVariable(name = "game-name") Title title) {

        Title.checkTitle(title);

        List<GameScore> allScore = gameScoreService.findAllScoreOf(title);
        List<Long> collect = allScore.stream()
                .map(GameScore::getScore)
                .collect(Collectors.toList());

        return responseService.getListResult(collect);
    }

    /********************************** 입력 ***************************************/
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게임 점수 입력", notes = "해당 게임 점수를 등록한다.")
    @PostMapping(value = "/game/{game-name}/score")
    public SingleResult<GameScoreDTO> saveScore(
            @ApiParam(value = "게임 타이틀", required = true) @PathVariable(name = "game-name") Title title,
            @ApiParam(value = "게임 점수", required = true) @RequestParam Long score) {

        Title.checkTitle(title);

        GameScore gameScore = gameScoreService.saveGameScore(title, score);
        GameScoreDTO gameScoreDTO = GameScoreDTO.createGameScoreDTO(gameScore);

        return responseService.getSingleResult(gameScoreDTO);
    }
}
