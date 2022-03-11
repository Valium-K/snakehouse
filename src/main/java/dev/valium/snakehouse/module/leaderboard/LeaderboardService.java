package dev.valium.snakehouse.module.leaderboard;

import dev.valium.snakehouse.module.game.score.GameScoreRepository;
import dev.valium.snakehouse.module.member.Member;
import dev.valium.snakehouse.module.member.MemberRepository;
import dev.valium.snakehouse.module.member.MemberService;
import dev.valium.snakehouse.module.member.exception.NoSuchMemberException;
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
        return leaderboardRepository.findFirstByMemberId(memberId)
                .orElseThrow(() -> new NoSuchMemberException(memberId));
    }

    @Transactional(readOnly = true)
    public List<Leaderboard> findAllHistory(Long memberId) {
        // member가 있는지 먼저 찾는다. - 추가쿼리
        Member member = memberService.findMember(memberId);

        return leaderboardRepository.findAllByMemberId(member.getId());
    }
}
