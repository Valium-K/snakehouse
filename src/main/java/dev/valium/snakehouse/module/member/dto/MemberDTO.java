package dev.valium.snakehouse.module.member.dto;

import dev.valium.snakehouse.module.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder(access = AccessLevel.PRIVATE)
public class MemberDTO {
    private Long id;
    private String name;

    public static MemberDTO createMemberDTO(Long id, String name) {
        return MemberDTO.builder().id(id).name(name).build();
    }
    public static MemberDTO createMemberDTO(Member member) {
        return MemberDTO.builder().id(member.getId()).name(member.getName()).build();
    }
}
