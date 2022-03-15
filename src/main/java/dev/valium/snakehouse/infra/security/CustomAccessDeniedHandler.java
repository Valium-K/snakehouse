package dev.valium.snakehouse.infra.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Jwt 토큰이 정상 && Jwt 토큰이 가지지 못한 권한의 리소스 접근의 경우
     * /exception/access-denied로 리다이렉트
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException,
            ServletException {
        response.sendRedirect("/exception/access-denied");
    }
}