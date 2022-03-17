package dev.valium.snakehouse.module.api.v1.leaderboard;

import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ListResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.model.response.SingleResult;
import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.leaderboard.dto.HistoryDTO;
import dev.valium.snakehouse.module.leaderboard.Leaderboard;
import dev.valium.snakehouse.module.leaderboard.LeaderboardService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"3. Leaderboard"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "마지막 플레이 기록 조회", notes = "회원의 마지막 플레이 기록을 조회한다.")
    @GetMapping(value = "/members/{id}/history/latest")
    public SingleResult<HistoryDTO> findLatestHistoryBy(
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "id") String memberId) {

        Leaderboard latestHistory = leaderboardService.findLatestHistory(memberId);

        return responseService.getSingleResult(HistoryDTO.createHistoryDTO(latestHistory));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "전체 플레이 기록 조회", notes = "회원의 전체 플레이 기록을 조회한다.")
    @GetMapping(value = "/members/{id}/history")
    public ListResult<HistoryDTO> findAllHistoryBy (
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "id") String memberId,
            @ApiParam(value = "정렬", allowableValues = "desc, asc") @RequestParam(name = "order", required = false) String orderBy) {

        List<Leaderboard> allHistory = leaderboardService.findAllHistory(memberId, orderBy);
        List<HistoryDTO> collect = allHistory.stream()
                .map(HistoryDTO::createHistoryDTO)
                .collect(Collectors.toList());

        return responseService.getListResult(collect);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원의 게임 점수 입력", notes = "회원의 해당 게임 점수를 입력한다.")
    @PostMapping(value = "/members/game/{title}/score/{score}")
    public CommonResult saveMemberGameScore (
            @ApiParam(value = "게임 타이틀", required = true) @PathVariable(name = "title") Title title,
            @ApiParam(value = "게임 점수", required = true) @PathVariable(name = "score") Long score) {

        Title.checkTitle(title);

        leaderboardService.saveLeaderboard(title, score);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "전체 플레이 기록 삭제", notes = "회원의 전체 플레이 기록을 삭제한다.")
    @DeleteMapping(value = "/members/{id}/history")
    public CommonResult deleteAllHistory (
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "id") String memberId) {

        leaderboardService.deleteAllHistory(memberId);

        return responseService.getSuccessResult();
    }
}
