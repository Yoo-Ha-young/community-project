package dev.com.community.community.service;

import dev.com.community.community.model.BoardDTO;
import dev.com.community.community.repository.BoardRepository;
import dev.com.community.community.model.BoardUpdateDTO;
import dev.com.community.community.entity.BoardEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 글 추가
    public BoardEntity save(BoardDTO request) {
        return boardRepository.save(request.toEntity());
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
