package dev.valium.snakehouse.infra.security;


import dev.valium.snakehouse.infra.jwt.JwtAuthenticationFilter;
import dev.valium.snakehouse.infra.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final JwtTokenProvider tokenProvider;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .httpBasic().disable()
                // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .csrf().disable()
                // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                // 다음 리퀘스트에 대한 사용권한 체크
                .authorizeRequests()
                // 가입 및 인증 주소는 누구나 접근가능
                .antMatchers("/*/sign-in", "/*/sign-in/**", "/*/sign-up", "/*/sign-up/**", "/oauth/**").permitAll()
                // 해당 경로 GET요청은 누구나 가능
                .antMatchers(HttpMethod.GET, "/hello-api/**", "/robots.txt", "/exception/**").permitAll()

                // 관리자 기능
                .antMatchers(HttpMethod.POST, "/*/members").hasRole("ADMIN")

                // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .anyRequest().hasRole("USER")

                // 인증 예외 발생 시 /exception/entrypoint 으로 리다이렉트
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                // 인가 예외 발생 시 /exception/access-denied 으로 리다이렉트
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())

                // jwt token 필터를 id/password 인증 필터 전에 넣는다
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        return jdbcTokenRepository;
    }
}