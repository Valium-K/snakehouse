package dev.valium.snakehouse.module.member;

import dev.valium.snakehouse.module.member.exception.NoSuchMemberException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    private final String UNKNOWN_MEMBER = "asfoiwen3409nwe=2j";

    @BeforeEach
    void beforeEach() {
        Member member = Member.createMember("Member1", "password", "mem");
        memberService.saveMember(member);
    }

    @AfterEach
    void afterEach() {
        Member member = memberRepository.findAll().get(0);
        memberRepository.delete(member);
    }

    @Test @DisplayName("ID로_맴버조회_실패")
    public void ID로_맴버조회_실패() throws Exception {
        Assertions.assertThatExceptionOfType(NoSuchMemberException.class)
                .isThrownBy(() -> memberService.findMember(UNKNOWN_MEMBER));
    }


}