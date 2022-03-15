package dev.valium.snakehouse.module.member;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "member_pk")
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String memberId;

    @Column(nullable = false, length = 100)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = true, length = 100)
    private String password;

    @Column(length = 100)
    private String provider;

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

    public static Member createSocialMember(String memberId, String name, String provider) {
        return Member.builder()
                .memberId(memberId)
                .password(provider) // UserDetails가 password null을 허용 안한다. dummy값을 provier로 정함.
                .provider(provider)
                .roles(Collections.singletonList("ROLE_USER"))
                .name(name)
                .build();
    }
}
