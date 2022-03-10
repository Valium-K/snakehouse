package dev.valium.snakehouse.module.member;

import dev.valium.snakehouse.module.member.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new NoSuchMemberException(id));
    }

    @Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Member modifyMember(Member fromMember, Member toMember) {
        fromMember.setName(toMember.getName());

        return fromMember;
    }
}
