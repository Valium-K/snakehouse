package dev.valium.snakehouse.module.member;

import dev.valium.snakehouse.module.member.exception.DuplicatedMemberException;
import dev.valium.snakehouse.module.member.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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
        if(predicateByMemberId(member.getMemberId())) {
            throw new DuplicatedMemberException("이미 존재하는 회원입니다.");
        }

        return memberRepository.save(member);
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    public Member modifyMember(Member fromMember, Member toMember) {
        if (fromMember.getName() != null && !fromMember.getName().equals(toMember.getName())) {
            fromMember.setName(toMember.getName());
        }
        if (fromMember.getPassword() != null && !fromMember.getPassword().equals(toMember.getPassword())) {
            fromMember.setPassword(toMember.getPassword());
        }

        return fromMember;
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