package dev.valium.snakehouse.module.leaderboard;

import dev.valium.snakehouse.module.game.Title;
import dev.valium.snakehouse.module.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    @EntityGraph(attributePaths = {"gameScore"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Leaderboard> findFirstByMemberIdOrderByCreatedDateDesc(Long memberId);
    @EntityGraph(attributePaths = {"gameScore"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Leaderboard> findAllByMemberIdOrderByCreatedDateDesc(Long memberId);
    @EntityGraph(attributePaths = {"gameScore"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Leaderboard> findAllByMemberIdOrderByCreatedDate(Long memberId);
    @Modifying @Query("delete from Leaderboard as lb where lb in :lbs")
    void deleteAllHistory(@Param("lbs") List<Leaderboard> leaderboards);
    @Query("select lb from Leaderboard as lb join fetch lb.member as m where m.memberId = :memberId")
    List<Leaderboard> findAllByMemberId(@Param("memberId") String memberId);
    Slice<Leaderboard> findAllByMember(Member member, Pageable pageable);
    @Query("select lb from Leaderboard as lb join fetch lb.gameScore as gs where gs.title = :title")
    List<Leaderboard> findAllByTitle(@Param("title") Title title);
    @Query("select lb from Leaderboard as lb join fetch lb.gameScore as gs where gs.title = :title")
    Slice<Leaderboard> findAllByTitle(@Param("title") Title title, Pageable pageable);

//    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.LOAD)
//    Slice<Leaderboard> findAllFetchMember(Pageable pageable);
}
