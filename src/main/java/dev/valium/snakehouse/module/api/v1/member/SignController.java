package dev.valium.snakehouse.module.api.v1.member;

import dev.valium.snakehouse.infra.jwt.JwtTokenProvider;
import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.model.response.SingleResult;
import dev.valium.snakehouse.module.member.Member;
import dev.valium.snakehouse.module.member.MemberDetailsService;
import dev.valium.snakehouse.module.member.MemberService;
import dev.valium.snakehouse.module.member.MemberUser;
import dev.valium.snakehouse.module.member.exception.LogInException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"4. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final MemberDetailsService memberDetailsService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;


    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/sign-in")
    public SingleResult<String> signIn(
            @ApiParam(value = "회원 ID", required = true) @RequestParam String memberId,
            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password) {

        MemberUser memberUser = memberDetailsService.loadUserByUsername(memberId);

        if(!passwordEncoder.matches(password, memberUser.getPassword())) {
            throw new LogInException("ID가 존재하지 않거나 password가 일치하지 않습니다.");
        }

        return responseService.getSingleResult(jwtTokenProvider
                .createToken(String.valueOf(memberUser.getMember().getId()), memberUser.getMember().getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/sign-up")
    public CommonResult signUp(
            @ApiParam(value = "회원 ID", required = true) @RequestParam String memberId,
            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {

        memberService.saveMember(Member.createMember(memberId, passwordEncoder.encode(password), name));

        return responseService.getSuccessResult();
    }
}