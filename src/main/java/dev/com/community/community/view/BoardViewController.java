package dev.com.community.community.view;

import static java.util.stream.Collectors.toList;

import dev.com.community.community.entity.BoardEntity;
import dev.com.community.community.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/boards")
@Controller
public class BoardViewController {
    private final BoardService boardService;

    // 글 추가 api
    @GetMapping
    public String getBoards(Model model){
        List<BoardViewDTO> boards =
            boardService.findAll().stream()
                .map(BoardViewDTO::new)
                .collect(toList());

        model.addAttribute("boards", boards);

        return "boardList";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable Long id, Model model) {
        BoardEntity board = boardService.findById(id);
        model.addAttribute("board", new BoardViewDTO(board));

        return "board";
    }

}
