package dev.com.community.user.config.jwt;

import dev.com.community.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenProvider { // 토큰 생성(공급) 클래스
    private final JwtProperties jwtProperties;

    // 유저 정보와 만료 기간을 전달해 토큰을 만들기
    public String generateToken(UserEntity user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // jwt 토큰 생성
    private String makeToken(Date expiry, UserEntity user) {
        Date now = new Date();

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(now)
            .setExpiration(expiry) //
            .setSubject(user.getEmail())
            .claim("id", user.getId())
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .compact();
    }

    // JWT 토큰 유효성 검증 메서드
    // 민료된 토큰이라면 유효성 검증에 실패, 유효하면 성공
    public boolean validToken(String token){
        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey()) // password 복호화
                .parseClaimsJws(token);
            return true;
        } catch (Exception e){ // 복호화 과정에서 에러발생 시 유효하지 않은 토큰 에러처리
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
            new SimpleGrantedAuthority("ROLE_USER"));

        // 스프링 시큐리티의 User
        return new UsernamePasswordAuthenticationToken(new User (
            claims.getSubject(), "", authorities), token, authorities);
    }

    // 토큰 기반으로 유저 ID 가져옴
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // 클레임을 가져옴
    private Claims getClaims(String token){
        return Jwts.parser() // 클레임 조회
            .setSigningKey(jwtProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody();
    }


    // 요청에서 토큰 추출하는 메서드 - 로그아웃 시 사용
    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
