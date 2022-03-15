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
import dev.valium.snakehouse.module.oauth.social.kakao.KakaoService;
import dev.valium.snakehouse.module.oauth.social.common.Profile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final KakaoService kakaoService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/sign-in")
    public SingleResult<String> signIn(
            @ApiParam(value = "회원 ID", required = true) @RequestParam String memberId,
            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password) {

        MemberUser memberUser = memberDetailsService.loadUserByUsername(memberId);

        if(!passwordEncoder.matches(password, memberUser.getPassword())) {
            throw new LogInException("ID가 존재하지 않거나 password가 일치하지 않습니다.");
        }

        return responseService.getSingleResult(getToken(memberUser.getMember().getMemberId(), memberUser.getMember().getRoles()));
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/sign-in/{provider}")
    public SingleResult<String> signInByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        Profile profile = kakaoService.getProfile(accessToken);
        Member member = memberService.findByMemberIdAndProvider(String.valueOf(profile.getId()), provider);

        return responseService.getSingleResult(getToken(member.getMemberId(), member.getRoles()));
    }

    private String getToken(String memberId, List<String> roles) {
        return jwtTokenProvider.createToken(memberId, roles);
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/sign-up")
    public CommonResult signUp(
            @ApiParam(value = "회원 ID", required = true) @RequestParam String memberId,
            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {

        if(password != null && !password.equals("")) {
            memberService.saveMember(Member.createMember(memberId, passwordEncoder.encode(password), name));
        }

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/sign-up/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
                                       @ApiParam(value = "이름", required = true) @RequestParam String name) {

        Profile profile = kakaoService.getProfile(accessToken);

        // TODO 커스텀 예외로 빼기.
        if(memberService.predicateByMemberIdAndProvider(String.valueOf(profile.getId()), provider)) {
            return responseService.getFailResult(-1, "이미 가입한 회원입니다.");
        }

        memberService.saveMember(Member.createSocialMember(String.valueOf(profile.getId()), name, provider));

        return responseService.getSuccessResult();
    }
}