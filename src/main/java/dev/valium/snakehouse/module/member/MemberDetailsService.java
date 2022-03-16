package dev.valium.snakehouse.module.member;

import dev.valium.snakehouse.module.member.exception.LogInException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public MemberUser loadUserByUsername(String username) {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new LogInException("ID가 존재하지 않거나 password가 일치하지 않습니다."));

        return new MemberUser(member);
    }
}