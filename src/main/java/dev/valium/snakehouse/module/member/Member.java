package dev.valium.snakehouse.module.member;

import dev.valium.snakehouse.module.base.BaseEntity;
import dev.valium.snakehouse.module.leaderboard.Leaderboard;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 30, name = "member_uid")
    private String memberId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) { this.password = password; }

    public static Member createMember(String memberId, String encryptedPassword, String name) {
        return Member.builder()
                .memberId(memberId)
                .password(encryptedPassword)
                .roles(Collections.singletonList("ROLE_USER"))
                .name(name)
                .build();
    }
}
