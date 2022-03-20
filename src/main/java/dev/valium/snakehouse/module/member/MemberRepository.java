package dev.valium.snakehouse.module.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member as m " +
            "join fetch m.roles " +
            "where m.memberId = :memberId")
    Optional<Member> findByMemberId(@Param("memberId") String memberId);
    @Query("select m from Member as m " +
            "join fetch m.roles " +
            "where m.memberId = :memberId " +
            "and m.provider = :provider")
    Optional<Member> findByMemberIdAndProvider(@Param("memberId") String memberId, @Param("provider") String provider);
    @Modifying @Query("delete from Member as m where m.memberId = :memberId")
    void deleteByMemberId(@Param("memberId") String memberId);
}
