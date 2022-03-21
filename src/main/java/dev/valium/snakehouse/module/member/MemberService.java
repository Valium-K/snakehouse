package dev.valium.snakehouse.module.member;

import dev.valium.snakehouse.infra.redis.CacheKey;
import dev.valium.snakehouse.module.leaderboard.Leaderboard;
import dev.valium.snakehouse.module.leaderboard.LeaderboardRepository;
import dev.valium.snakehouse.module.member.exception.DuplicatedMemberException;
import dev.valium.snakehouse.module.member.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LeaderboardRepository leaderboardRepository;
    private final EntityManager em;

    @Value("${spring.jpa.properties.hibernate.default_batch_fetch_size}")
    private Long BATCH_SIZE;

    @Transactional(readOnly = true)
    public Member findMember(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchMemberException(memberId));
    }

    @Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {
        if(predicateByMemberId(member.getMemberId())) {
            throw new DuplicatedMemberException("이미 존재하는 회원입니다.");
        }

        return memberRepository.save(member);
    }

    @CacheEvict(value = CacheKey.MEMBER, key = "#memberId")
    public void deleteMemberByMemberId(String memberId) {

        // leaderboard 삭제 jpa in query
        List<Leaderboard> lbs = leaderboardRepository.findAllByMemberId(memberId);

        if(!lbs.isEmpty()) {
            leaderboardRepository.deleteAllHistory(lbs);
            memberRepository.delete(lbs.get(0).getMember());
        }
        else {
            Member member = findMember(memberId);
            memberRepository.delete(member);
        }
    }

    @CachePut(value = CacheKey.MEMBER, key = "#toUpdateMember.memberId")
    public Member modifyMember(Member toUpdateMember, Member propsMember) {

        if (propsMember.getName() != null) {
            toUpdateMember.setName(propsMember.getName());
        }
        if (propsMember.getPassword() != null) {
            toUpdateMember.setPassword(propsMember.getPassword());
        }

        // OSIV, 영속성 컨테이너 앞 redis 프록시의 이유로 컨트롤러에서 넘어오는 entity는 현재 detached 이다.
        // 이 상황에서 변경감지를 사용하려면 select 쿼리가 추가로 필요하므로 그냥 ORM 특성을 조금 무시하고 JPQL을 날렸다.
        em.createQuery("update Member as m " +
                                "set m.name = :name, m.password = :password " +
                                "where m.id = :id")
                .setParameter("name", toUpdateMember.getName())
                .setParameter("password", toUpdateMember.getPassword())
                .setParameter("id", toUpdateMember.getId())
                .executeUpdate();

        return toUpdateMember;
    }

    @CacheEvict(value = CacheKey.MEMBER, key = "#memberId")
    public void deleteAllMembers(String memberId) {
        leaderboardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    /**
     * Only for Oauth signed member
     * @param memberId
     * @param provider
     * @return
     */
    public Member findByMemberIdAndProvider(String memberId, String provider) {
        return memberRepository.findByMemberIdAndProvider(memberId, provider)
                .orElseThrow(NoSuchMemberException::new);
    }

    /**
     * Only for Oauth signed member
     * predicates member who has signed up through social media account
     * @param memberId
     * @param provider
     * @return
     */
    public boolean predicateByMemberIdAndProvider(String memberId, String provider) {
        Optional<Member> member = memberRepository.findByMemberIdAndProvider(memberId, provider);

        return member.isPresent();
    }
    public boolean predicateByMemberId(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);

        return member.isPresent();
    }
}