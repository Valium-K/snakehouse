package dev.valium.snakehouse.module.api.v1.leaderboard;

import dev.valium.snakehouse.module.api.model.response.ListResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.model.response.SingleResult;
import dev.valium.snakehouse.module.leaderboard.dto.HistoryDTO;
import dev.valium.snakehouse.module.leaderboard.Leaderboard;
import dev.valium.snakehouse.module.leaderboard.LeaderboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"3. Leaderboard"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;
    private final ResponseService responseService;

    @ApiOperation(value = "마지막 플레이 기록 조회", notes = "회원의 마지막 플레이 기록을 조회한다.")
    @GetMapping(value = "/members/{id}/history/latest")
    public SingleResult<HistoryDTO> findLatestHistoryBy(
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "id") Long id) {

        Leaderboard latestHistory = leaderboardService.findLatestHistory(id);

        return responseService.getSingleResult(HistoryDTO.createHistoryDTO(latestHistory));
    }

    @ApiOperation(value = "전체 플레이 기록 조회", notes = "회원의 전체 플레이 기록을 조회한다.")
    @GetMapping(value = "/members/{id}/history")
    public ListResult<HistoryDTO> findAllHistoryBy (
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "id") Long id) {

        List<Leaderboard> allHistory = leaderboardService.findAllHistory(id);
        List<HistoryDTO> collect = allHistory.stream()
                .map(HistoryDTO::createHistoryDTO)
                .collect(Collectors.toList());

        return responseService.getListResult(collect);
    }
}
