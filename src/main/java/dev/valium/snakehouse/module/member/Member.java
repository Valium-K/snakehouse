package dev.valium.snakehouse.module.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.valium.snakehouse.module.base.BaseEntity;
import dev.valium.snakehouse.module.leaderboard.Leaderboard;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter @Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
// Redis 사용시 lazy 로딩은 캐싱에러로 이어진다.
// member 클래스는 다른 클래스에서 lazy 로딩 하므로 해당 설정을 추가한다.
@Proxy(lazy = false)
public class Member extends BaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_pk")
    private Long id;

    @Column(nullable = false, unique = true, length = 30, updatable = false)
    private String memberId;

    @Column(nullable = false, length = 100)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = true, length = 100)
    private String password;

    @Column(length = 100, updatable = false)
    private String provider;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "member_roles_id")
    )
    @Column(name = "roles")
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
