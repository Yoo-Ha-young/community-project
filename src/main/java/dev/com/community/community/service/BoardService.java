package dev.com.community.community.service;

import dev.com.community.community.model.BoardDTO;
import dev.com.community.community.repository.BoardRepository;
import dev.com.community.community.model.BoardUpdateDTO;
import dev.com.community.community.entity.BoardEntity;
import dev.com.community.user.entity.UserEntity;
import dev.com.community.user.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 로그인 된 사용자만 글 추가가 가능
    public BoardEntity save(BoardDTO board, Principal principal) {
        String userEmail = principal.getName();
        UserEntity user = userRepository.findByEmail(board.toEntity().getUserEntity().getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 사용자를 찾을 수 없습니다: " + userEmail));

//        board.toEntity().setUserEntity(user);
        return boardRepository.save(board.toEntity());
    }

    // 목록 조회
    public List<BoardEntity> findAll() {
        return boardRepository.findAll();
    }

    // 조회
    public BoardEntity findById(long id){
        return boardRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("not found:" +id));
    }

    public void delete(long id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardEntity update(long id, BoardUpdateDTO boardUpdateDTO){
        BoardEntity board = boardRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("board" + id + "not found"));

        board.update(boardUpdateDTO.getTitle(), boardUpdateDTO.getContent());

        return board;
    }


}
