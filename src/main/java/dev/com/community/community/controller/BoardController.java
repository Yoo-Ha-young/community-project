package dev.com.community.community.controller;


import static java.util.stream.Collectors.toList;

import dev.com.community.community.model.BoardDTO;
import dev.com.community.community.entity.BoardEntity;
import dev.com.community.community.model.BoardResponseDTO;
import dev.com.community.community.service.BoardService;
import dev.com.community.community.model.BoardUpdateDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    // 글 추가 api
    @PostMapping
    public ResponseEntity<BoardEntity> addBoard(@RequestBody BoardDTO addBoardDTO){
        BoardEntity savedBoardEntity = boardService.save(addBoardDTO);
        return  ResponseEntity.status(HttpStatus.CREATED)
            .body(savedBoardEntity);
    }

    // 조회 api
    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> findAllBoards() {
        List<BoardResponseDTO> boards = boardService.findAll()
            .stream()
            .map(BoardResponseDTO::new)
            .collect(toList());

        return ResponseEntity.ok()
            .body(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> findBoard(@PathVariable long id){
        BoardEntity board = boardService.findById(id);

        return ResponseEntity.ok()
            .body(new BoardResponseDTO(board));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable long id){
        boardService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable long id, @RequestBody BoardUpdateDTO boardUpdateDTO){
        BoardEntity updatedBoard = boardService.update(id, boardUpdateDTO);

        return ResponseEntity.ok()
            .body(updatedBoard);
    }

}

/*
* 200 OK : 요청이 성공적으로 수행
* 201 Created : 요청이 성공적으로 수행, 새로운 리소스 생성
* 400 Bad Request : 요청 값이 잘못되어 요청에 실패
* 403 Forbidden : 권한이 없어 요청에 실패
* 404 Not Found : 요청 값으로 찾은 리소스가 없어 요청에 실패
* 500 Internal Server Error : 서버 상에 문제가 있어 요청에 실패
*/
