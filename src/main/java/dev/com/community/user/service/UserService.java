package dev.com.community.user.service;

import dev.com.community.community.entity.BoardEntity;
import dev.com.community.community.model.BoardResponseDTO;
import dev.com.community.community.repository.BoardRepository;
import dev.com.community.user.config.jwt.JwtProperties;
import dev.com.community.user.entity.RoleEntity;
import dev.com.community.user.entity.UserEntity;
import dev.com.community.user.model.UserRegisterDTO;
import dev.com.community.user.model.UserResponseDTO;
import dev.com.community.user.repository.RoleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.management.relation.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dev.com.community.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    // 회원가입 - 유저 정보 저장
    public UserEntity addUser(UserRegisterDTO userRegisterDTO) {
        RoleEntity role = roleRepository.findById(userRegisterDTO) // TODO ; dto에 추가, role 추가 구현
            .orElseThrow(() -> new RoleNotFoundException("해당 롤을 찾을 수 없습니다."));

        return userRepository.save(
            UserEntity.builder()
                .email(userRegisterDTO.getEmail())
                .username(userRegisterDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()))
                .phone(userRegisterDTO.getPhone())
                .role(role)
                .build()
        );
    }

    // 유저의 글 목록 전체 조회하기
    // TODO : test 필요
    public List<BoardResponseDTO> findUserBoards(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("사용자 정보가 없습니다."));

        List<BoardEntity> boardList = boardRepository.findBoardEntityByUserEntity(user);

        List<BoardResponseDTO> boardResponseDTOList = new ArrayList<>();
        boardList.stream().forEach((e) -> {
            boardResponseDTOList.add(BoardResponseDTO.of(e));
        });

        return boardResponseDTOList;
    }

    // 리프레시 토큰을 전달받아 토큰제공자를 사용해서 새로운 액세스 토큰을 만드는 메서드
    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

}
