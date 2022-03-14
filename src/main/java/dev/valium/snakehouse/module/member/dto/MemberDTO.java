package dev.valium.snakehouse.module.member.dto;

import dev.valium.snakehouse.module.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder(access = AccessLevel.PRIVATE)
public class MemberDTO {
    private String memberId;
    private String name;

    public static MemberDTO createMemberDTO(String memberId, String name) {
        return MemberDTO.builder().memberId(memberId).name(name).build();
    }
    public static MemberDTO createMemberDTO(Member member) {
        return MemberDTO.builder().memberId(member.getMemberId()).name(member.getName()).build();
    }
}
