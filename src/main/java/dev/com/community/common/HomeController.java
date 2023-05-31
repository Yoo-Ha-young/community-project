package dev.com.community.common;

import dev.com.community.user.entity.UserEntity;
import dev.com.community.user.model.UserRegisterDTO;
import dev.com.community.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class HomeController {

    private final UserService userService;

    // 기본 홈 경로
    @GetMapping
    public ResponseEntity<?> communityHome(){
        return ResponseEntity.ok().body("커뮤니티에 접속 완료.");
    }

    // 회원가입
    @PostMapping("/signup") // @Valid 추가 예정
    public ResponseEntity<UserEntity> signupAdd(@RequestBody UserRegisterDTO registerRequest) {
        UserEntity savedUser = userService.addUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

}