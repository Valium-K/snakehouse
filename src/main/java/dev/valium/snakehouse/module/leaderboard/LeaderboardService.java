package dev.valium.snakehouse.module.leaderboard;

import dev.valium.snakehouse.module.common.PageRequestProvider;
import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.game.score.GameScore;
import dev.valium.snakehouse.module.game.score.exception.NoPlayHistoryException;
import dev.valium.snakehouse.module.member.Member;
import dev.valium.snakehouse.module.member.MemberService;
import dev.valium.snakehouse.module.security.SecurityContextHolderHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public Leaderboard findLatestHistory(String memberId) {
        // member가 있는지 먼저 찾는다. - 추가쿼리
        Member member = memberService.findMember(memberId);

        return leaderboardRepository.findFirstByMemberIdOrderByCreatedDateDesc(member.getId())
                .orElseThrow(() -> new NoPlayHistoryException("memberId: " + memberId + "의 플레이 기록이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Leaderboard> findAllHistory(String memberId, String orderBy, Integer page) {
        // member가 있는지 먼저 찾는다. - 추가쿼리
        Member member = memberService.findMember(memberId);

        // 전체 조회
        if (page == null)
            return ("desc".equals(orderBy))
                    ? leaderboardRepository.findAllByMemberIdOrderByCreatedDateDesc(member.getId())
                    : leaderboardRepository.findAllByMemberIdOrderByCreatedDate(member.getId());

        // 페이징
        PageRequest pageRequest = PageRequestProvider.get(orderBy, page, "createdDate");

        return leaderboardRepository.findAllByMember(member, pageRequest).getContent();
    }

    public void deleteAllHistory(String memberId) {
        List<Leaderboard> allHistory = findAllHistory(memberId, "It doesn't matter", null);

        leaderboardRepository.deleteAllHistory(allHistory);
    }

    @Transactional(readOnly = true)
    public List<Leaderboard> findAllMemberByTitle(Title title) {
        // Leaderboard + GameScore + (왜 member까지 조회하는지 모르겠다 lazy로 설정했는데)
        List<Leaderboard> allByTitle = leaderboardRepository.findAllByTitle(title);

        Set<Member> memberSet = new HashSet<>();
        List<Leaderboard> leaderboards = new ArrayList<>();

        allByTitle.forEach(lb -> {
            if(!memberSet.contains(lb.getMember())) {
                memberSet.add(lb.getMember());
                leaderboards.add(lb);
            }
        });

        return leaderboards;
    }

    public Leaderboard saveMemberGameScore(String memberId, Title title, Long score) {
        Member member = memberService.findMember(memberId);
        GameScore gameScore = GameScore.createGameScore(title, score);
        Leaderboard leaderboard = Leaderboard.createLeaderboard(member, gameScore);

        return leaderboardRepository.save(leaderboard);
    }

    public Leaderboard saveLeaderboard(Title title, Long score) {

        Member member = SecurityContextHolderHelper.getMember();
        GameScore gameScore = GameScore.createGameScore(title, score);
        Leaderboard leaderboard = Leaderboard.createLeaderboard(member, gameScore);

        return leaderboardRepository.save(leaderboard);
    }
}
