package dev.com.community.user.service;

import dev.com.community.user.entity.UserEntity;
import dev.com.community.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override // 오버라이드는 Username 컬럼이 아닌, 고유한 값인 email로 가져옴
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 없습니다."));

        // 사용자의 역할 정보를 가져와서 SimpleGrantedAuthority 객체로 변환
        List<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(userEntity.getRole().getName())
        );

        // UserDetails 객체 생성
        return new org.springframework.security.core.userdetails.User(
            userEntity.getEmail(),
            userEntity.getPassword(),
            authorities
        );
    }

}
