package dev.com.community.user.config;


import dev.com.community.user.config.jwt.TokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Configuration
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    // TODO : 액세스 토큰값이 담긴 Authorization 헤더값을 가져온 뒤 액세스 토큰이 유효하다면 인증 정보 설정

    private final TokenProvider tokenProvider;
    // 헤더값
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더의 Authorization 키의 값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // 가져온 값에서 접두사 Bearer를 제거
        String token = getAccessToken(authorizationHeader);
        // 가져온 토큰이 유효한지 확인 -> 유효한 떄는 인증 정보를 설정
        if(tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
    // 요청 헤더에서 키가 Authorization인 필드의 값을
    // 가져온 다음 토큰의 접두사 Bearer를 제외

    private String getAccessToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null; // 값이 null이거나 Bearer로 시작하지 않으면 null 반환
    }
}
