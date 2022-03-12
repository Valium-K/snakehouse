package dev.valium.snakehouse.module.leaderboard;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
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
    List<Leaderboard> findAllByMemberId(Long memberId);
    @Query("delete from Leaderboard as lb where lb in :lbs")
    void deleteAllHistory(@Param("lbs") List<Leaderboard> leaderboards);
}
