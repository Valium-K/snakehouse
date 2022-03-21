package dev.valium.snakehouse.module.api.v1.leaderboard;

import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.v1.common.UnsupportedController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"3. Leaderboard"})
@RestController
@RequestMapping(value = "/v1")
public class UnsupportedLeaderboardController extends UnsupportedController {

    public UnsupportedLeaderboardController(ResponseService responseService) {
        super(responseService);
    }

    /********************************** 조회 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @GetMapping(value = "/members/game/{title}/score/{score}")
    public CommonResult getMembersTitleScore() {
        return unSupportedMethod();
    }

    /********************************** 입력 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @PostMapping(value = "/members/{id}/history")
    public CommonResult postMemberHistory() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PostMapping(value = "/members/{id}/history/latest")
    public CommonResult postMemberLatestHistory() {
        return unSupportedMethod();
    }

    /********************************** 수정 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @PutMapping(value = "/members/game/{title}/score/{score}")
    public CommonResult putMembersTitleScore() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PutMapping(value = "/members/{id}/history")
    public CommonResult putMemberHistory() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PutMapping(value = "/members/{id}/history/latest")
    public CommonResult putMemberLatestHistory() {
        return unSupportedMethod();
    }

    /********************************** 삭제 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @DeleteMapping(value = "/members/game/{title}/score/{score}")
    public CommonResult deleteMembersTitleScore() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @DeleteMapping(value = "/members/{id}/history/latest")
    public CommonResult deleteMemberLatestHistory() {
        return unSupportedMethod();
    }
}
