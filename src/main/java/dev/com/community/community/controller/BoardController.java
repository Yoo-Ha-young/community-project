package dev.com.community.community.controller;


import static java.util.stream.Collectors.toList;

import dev.com.community.community.model.BoardDTO;
import dev.com.community.community.entity.BoardEntity;
import dev.com.community.community.model.BoardResponseDTO;
import dev.com.community.community.service.BoardService;
import dev.com.community.community.model.BoardUpdateDTO;
import java.security.Principal;
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

    // 게시판 서비스 클래스에 이용자 정보에 대한 예외처리 부분 추가 구현
    // TODO : 연결 시키고 글 작성하나 해보기 - 확인

    // 게시글 작성
    @PostMapping
    public ResponseEntity<BoardEntity> createBoard(
            Principal principal, @RequestBody BoardDTO board) {
        BoardEntity savedBoardEntity = boardService.save(board, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBoardEntity);
    }

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> findAllBoards() {
        List<BoardResponseDTO> boards;

        boards = boardService.findAll()
            .stream()
            .map(BoardResponseDTO::of)

            .collect(toList());

        return ResponseEntity.ok()
            .body(boards);
    }

    // 작성된 게시글 편집
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable long id, @RequestBody BoardUpdateDTO boardUpdateDTO){
        BoardEntity updatedBoard = boardService.update(id, boardUpdateDTO);

        return ResponseEntity.ok()
            .body(updatedBoard);
    }

    // TODO : 키워드로 게시물 검색 : 검색 결과를 게시글 목록(List)으로 보여준다.

    // id로 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> findBoard(@PathVariable long id){
        BoardEntity board = boardService.findById(id);
    
        return ResponseEntity.ok()
            .body(BoardResponseDTO.of(board));
    }

    // 선택 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable long id){
        boardService.delete(id);

        return ResponseEntity.ok().build();
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
