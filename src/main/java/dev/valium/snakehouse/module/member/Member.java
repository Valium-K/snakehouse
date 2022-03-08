package dev.valium.snakehouse.module.member;

import dev.valium.snakehouse.module.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String uid;

    @Column(nullable = false, length = 50)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public static Member createMember(String name) {
        return Member.builder()
                .name(name)
                .uid(UUID.randomUUID().toString().substring(0, 30))
                .build();
    }
}
