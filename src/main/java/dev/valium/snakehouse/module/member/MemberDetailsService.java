package dev.valium.snakehouse.module.member;

import dev.valium.snakehouse.infra.redis.CacheKey;
import dev.valium.snakehouse.module.member.exception.LogInException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // MemberUser를 캐싱 하지 않음
    @Override
    public MemberUser loadUserByUsername(String username) {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new LogInException("ID가 존재하지 않거나 password가 일치하지 않습니다."));

        return new MemberUser(member);
    }

    /**
     *  Redis는 같은 객체내의 메소드를 호출시 에노테이션이 동작하지 않는다.
     *  즉, 호출하는 메서드 내에 꼭 JPA쿼리 메소드가 들어있어야 한다는 의민데
     *  그렇게 되면 Member를 캐싱 하고싶어도 결과적으로 MemberUser가 캐싱된다.
     *  지금 생각나는 해결법은 아래와 같다
     *
     *  1. 프록시를 둔다.
     *  2. Member를 return하고 외부에서 MemberUser로 wrapping 한다.
     *
     *  현재 2번 방법으로 해결했다. 문제점: MemberUser를 현재 외부에서 생성한다.
     * @param memberId
     * @return
     */
    // 매 요청마다 토큰정보로 회원을 조회하는 메소드를 캐싱한다.
    // value: 키값, key: 추가 파라미터 정보(여기선 메소드 인자 변수명인 username), unless: 리턴되는 값이 null이 아닐경우에만 조회 후 캐싱한다.
    // 실 저장 객체는 메소드 리턴 타입임에 주의한다.
    @Cacheable(value = CacheKey.MEMBER, key = "#memberId", unless = "#result == null")
    public Member loadMemberByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new LogInException("ID가 존재하지 않거나 password가 일치하지 않습니다."));
    }
}