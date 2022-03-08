package dev.valium.snakehouse.module.api.v1.member;


import dev.valium.snakehouse.module.member.Member;
import dev.valium.snakehouse.module.member.MemberRepository;
import dev.valium.snakehouse.module.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;


    // Single Mapping
    @GetMapping(value = "/member")
    public Member findMember(@RequestParam(name = "id") Long id) {
        return memberService.findMember(id);
    }
    @PostMapping(value = "/member")
    public Member saveMember() {
        return memberService.saveMember(Member.createMember("asdf"));
    }


    @GetMapping(value = "/members")
    public List<Member> findAllMembers() {
        return memberService.findAllMembers();
    }


}
