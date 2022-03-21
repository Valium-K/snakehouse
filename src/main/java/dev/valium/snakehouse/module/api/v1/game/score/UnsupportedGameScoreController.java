package dev.valium.snakehouse.module.api.v1.game.score;

import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.v1.common.UnsupportedController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. GameScore"})
@RestController
@RequestMapping(value = "/v1")
public class UnsupportedGameScoreController extends UnsupportedController {

    public UnsupportedGameScoreController(ResponseService responseService) {
        super(responseService);
    }

    /********************************** 조회 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @GetMapping(value = "/game/{title}/score/{score}")
    public CommonResult getGameScore() {
        return unSupportedMethod();
    }

    /********************************** 입력 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @PostMapping(value = "/game")
    public CommonResult postGame() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PostMapping(value = "/game/{title}/high-score")
    public CommonResult postHighScore() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PostMapping(value = "/game/{title}/score")
    public CommonResult postScore() {
        return unSupportedMethod();
    }

    /********************************** 수정 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @PutMapping(value = "/game")
    public CommonResult putGame() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PutMapping(value = "/game/{title}/high-score")
    public CommonResult putHighScore() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PutMapping(value = "/game/{title}/score")
    public CommonResult putScore() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PutMapping(value = "/game/{title}/score/{score}")
    public CommonResult putGameScore() {
        return unSupportedMethod();
    }

    /********************************** 삭제 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @DeleteMapping(value = "/game")
    public CommonResult deleteGame() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @DeleteMapping(value = "/game/{title}/high-score")
    public CommonResult deleteHighScore() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @DeleteMapping(value = "/game/{title}/score")
    public CommonResult deleteScore() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @DeleteMapping(value = "/game/{title}/score/{score}")
    public CommonResult deleteGameScore() {
        return unSupportedMethod();
    }
}
