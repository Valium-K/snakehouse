package dev.valium.snakehouse.module.exception;


import dev.valium.snakehouse.module.api.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @RequestMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new AuthenticationEntryPointException("해당 리소스에 대한 접근 권한이 없습니다.");
    }

    @RequestMapping(value = "/access-denied")
    public CommonResult accessDeniedException() {
        throw new AccessDeniedException("보유한 권한으로 접근 할 수 없는 리소스 입니다.");
    }
}