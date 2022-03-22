package dev.valium.snakehouse.module.oauth.social.kakao;


import dev.valium.snakehouse.module.member.MemberService;
import dev.valium.snakehouse.module.member.exception.DuplicatedMemberException;
import dev.valium.snakehouse.module.oauth.social.common.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/oauth/login")
public class KakaoController {

    private final Environment env;
    private final KakaoService kakaoService;
    private final MemberService memberService;

    @Value("${spring.url.base}")
    private String baseUrl;
    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;
    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 카카오 로그인 페이지
     */
    @GetMapping
    public String socialLogin(Model model) {
        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);

        model.addAttribute("loginUrl", loginUrl);

        return "social/kakao/sign-up-pop-up";
    }

    /**
     * 카카오 인증 완료 후 리다이렉트 화면
     */
    @GetMapping("/kakao")
    public String redirectKakao(Model model, @RequestParam String code) {
        model.addAttribute("authInfo", kakaoService.getTokenInfo(code));
        model.addAttribute("host", contextPath);

        return "social/kakao/kakaoInfoDone";
    }

    /**
     * 계정 인증 후 기타 정보 기입
     * @param model
     * @param token
     * @return
     */
    @GetMapping("/kakao/finish")
    public String finishSignUp(Model model, @RequestParam String token) {

        Profile profile = kakaoService.getProfile(token);
        if(memberService.predicateByMemberIdAndProvider(String.valueOf(profile.getId()), "kakao")) {
            throw new DuplicatedMemberException("이미 가입한 회원입니다.");
        }

        model.addAttribute("token", token);

        return "social/kakao/finish-sign-up";
    }
}
