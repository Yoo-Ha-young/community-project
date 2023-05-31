package dev.com.community.user.config;

import dev.com.community.user.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userDetail;
    @Bean
    WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
            .antMatchers("/static/**");
    }

    @Bean // 특정 HTTP 요청에 대한 웹 기잔 보안 구성
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeRequests() // 인증, 인가 설정
            .antMatchers("/main").permitAll() // 첫 화면은 모든 사용자에게 허용
            .antMatchers("/user/login").permitAll() // 로그인 엔드포인트는 모든 사용자에게 허용
            .antMatchers("/main/signup").permitAll() // 회원가입 엔드포인트는 모든 사용자에게 허용
            .antMatchers("/normal-user").hasRole("USER") // "/normal-user" 엔드포인트는 "USER" 권한을 가진 사용자만 허용
            .antMatchers("/boards").permitAll() // "/boards" 엔드포인트는 모든 사용자에게 허용
            .antMatchers("/boards/board/save").hasRole("USER") // "/boards/board/save" 엔드포인트는 "USER" 권한을 가진 사용자만 허용
            .antMatchers("/api/**").authenticated() // 나머지 모든 요청은 인증된 사용자만 허용
            .and()
            .csrf().disable().build(); // CSRF 보안 기능 비활성화 (테스트 용도로만 사용)
    }

    // TODO :  인증관리자 관련 설정 확인 클래스
    @Bean // 인증 관리자 관련 설정
    public AuthenticationManager authenticationManager(HttpSecurity http,
        BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
        throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetail) // 사용자 정보 서비스 설정
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
