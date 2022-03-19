package dev.valium.snakehouse.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomBCryptPasswordEncoder extends BCryptPasswordEncoder {
    /**
     * API에서 회원 수정 기능의 회원 이름, 패스워드는 원하는 수정을 옵션으로 제공한다.
     * 그리고 소셜 로그인의 경우 패스워드가 필요없다.
     *
     * 그래서 password에 null을 허용한다면 해당 메소드를 이용하고
     * 그렇지 않다면 super의 encode()를 이용하도록 제공한다.
     * @param rawPassword
     * @return
     */
    public String nullableEncode(CharSequence rawPassword) {
        if (rawPassword == null) return null;
        else return super.encode(rawPassword);
    }
}
