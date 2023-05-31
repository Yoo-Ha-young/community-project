package dev.com.community.user.controller;

import dev.com.community.user.entity.UserEntity;
import dev.com.community.user.model.UserRegisterDTO;
import dev.com.community.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class UserApiController {

    private final UserService userService;

/*  가입한 email(아이디)로 사용자 정보 조회
//    @GetMapping("/{id}")
//    public void getUser(@PathVariable String email){
//        UserEntity user = userRepository.findByEmail(email){
//            .orElseThrow(()->new UsernameNotFoundException("사용자 정보가 없습니다."));
//        }
//        UserResponseDTO userResponseDTO =
    }
*/

    // TODO : 로그인 후 게시글 작성 및 수정 가능
    // TODO : 로그아웃 후 게시글 작성 및 수정 불가능

    // 유저의 게시판 조회
    @GetMapping("/normal-user/{id}/boards")
    public ResponseEntity<?> userBoards(@PathVariable Long id){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.findUserBoards(id));
    }
}