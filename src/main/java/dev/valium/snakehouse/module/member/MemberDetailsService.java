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

    // 매 요청마다 토큰정보로 회원을 조회하는 메소드를 캐싱한다.
    // value: 키값, key: 추가 파라미터 정보(여기선 메소드 인자 변수명인 username), unless: 리턴되는 값이 null이 아닐경우에만 조회 후 캐싱한다.
    @Cacheable(value = CacheKey.MEMBER, key = "#username", unless = "#result == null")
    @Override
    public MemberUser loadUserByUsername(String username) {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new LogInException("ID가 존재하지 않거나 password가 일치하지 않습니다."));

        return new MemberUser(member);
    }
}