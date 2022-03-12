package dev.valium.snakehouse.module.leaderboard;

import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.game.score.GameScore;
import dev.valium.snakehouse.module.game.score.exception.NoPlayHistoryException;
import dev.valium.snakehouse.module.member.Member;
import dev.valium.snakehouse.module.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public Leaderboard findLatestHistory(Long memberId) {
        // member가 있는지 먼저 찾는다. - 추가쿼리
        Member member = memberService.findMember(memberId);

        return leaderboardRepository.findFirstByMemberIdOrderByCreatedDateDesc(member.getId())
                .orElseThrow(() -> new NoPlayHistoryException("memberId: " + memberId + "의 플레이 기록이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Leaderboard> findAllHistory(Long memberId) {
        // member가 있는지 먼저 찾는다. - 추가쿼리
        Member member = memberService.findMember(memberId);

        return leaderboardRepository.findAllByMemberId(member.getId());
    }

    public void deleteAllHistory(Long memberId) {
        List<Leaderboard> allHistory = findAllHistory(memberId);

        leaderboardRepository.deleteAllHistory(allHistory);
    }

    public Leaderboard saveMemberGameScore(Long memberId, Title title, Long score) {
        Member member = memberService.findMember(memberId);
        GameScore gameScore = GameScore.createGameScore(title, score);
        Leaderboard leaderboard = Leaderboard.createLeaderboard(member, gameScore);

        return leaderboardRepository.save(leaderboard);
    }
}
