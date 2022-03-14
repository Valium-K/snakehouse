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
    public Member findMember(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchMemberException(memberId));
    }

    @Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    public Member modifyMember(Member fromMember, Member toMember) {
        if(fromMember.getName() != null && !fromMember.getName().equals(toMember.getName())) {
            fromMember.setName(toMember.getName());
        }
        if(fromMember.getPassword() != null && !fromMember.getPassword().equals(toMember.getPassword())) {
            fromMember.setPassword(toMember.getPassword());
        }

        return fromMember;
    }
}
