package dev.com.community.user.controller;

import dev.com.community.common.model.ResponseErrorDTO;
import dev.com.community.user.PasswordUtils;
import dev.com.community.user.config.jwt.TokenProvider;
import dev.com.community.user.entity.UserEntity;
import dev.com.community.user.exception.PasswordNotMatchException;
import dev.com.community.user.exception.UserEmailNotFoundException;
import dev.com.community.user.model.UserLoginDTO;
import dev.com.community.user.repository.UserRepository;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserLoginController {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @ExceptionHandler(value = {PasswordNotMatchException.class})
    public ResponseEntity<?> PasswordNotMatchExceptionHandler(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 로그인
    // 로그인 시 사용자 이메일과 비밀번호를 통해서 JWT를 발행하는 API
    // JWT 토큰 발생시 사용자 정보가 유효하지 않을 때 예외 발생
    @PostMapping("/user/login")
    public ResponseEntity<?> creatUserToken(@RequestBody @Valid UserLoginDTO userLogin,
        Errors errors) throws IOException {
        // 사용자 정보가 존재하지 않을 경우에는 예외 발생
        List<ResponseErrorDTO> responseErrorDTOList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorDTOList.add(ResponseErrorDTO.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrorDTOList, HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findByEmail(userLogin.getEmail())
            .orElseThrow(() -> new UserEmailNotFoundException("사용자 정보가 없습니다."));


        // 비밀번호가 일치하지 않는 경우 예외발생, 그리고 일치 한다면 토큰 발행
        // TODO 토큰 발행 : 토큰의 유효기한은 1달로 설정 : 리프레시 토큰 확인
        String token = tokenProvider.generateToken(userLogin, Duration.ofHours(30)); // 토큰
        if (!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok().body(token);
    }


    // 로그아웃
    // 로그아웃 API
    // TODO @RequestParam으로 특정 유저 id의 정보를 로그아웃 하는 메서드로 변경하기
    @PostMapping("/{id}/logout")
    public ResponseEntity<?> logout(@RequestParam Long id, HttpServletRequest request) {
        String token = tokenProvider.extractTokenFromRequest(request);
        // 인증 정보 제거
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("로그아웃되었습니다.");
    }

    @GetMapping("/normal-user")
    public ResponseEntity<?> getLoginPage(Authentication authentication) {
        boolean hasUserRole = authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
        if (hasUserRole) {
            return ResponseEntity.ok().body("로그인에 성공했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 리소스에 접근할 권한이 없습니다.");
        }
    }



}
