package dev.valium.snakehouse.infra.jwt;

import dev.valium.snakehouse.module.member.MemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class JwtTokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;
    private Long tokenValidMilliseconds = JwtConfig.TOKEN_VALID_MILLISECONDS;
    private final MemberDetailsService memberDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(date) // 토큰 발행일자
                .setExpiration(new Date(date.getTime() + tokenValidMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey) // secret값 암호화
                .compact();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = memberDetailsService.loadUserById(Long.valueOf(getUserPk(token)));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰으로 회원 추출
    public String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }





}