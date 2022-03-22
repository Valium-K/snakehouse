package dev.valium.snakehouse.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sign")
public class WebSignController {
    private final MemberService memberService;

    @GetMapping("/in")
    public String signIn() {
        return "sign-in";
    }

    @GetMapping("/up")
    public String SignUp() {
        return "sign-up";
    }
}
