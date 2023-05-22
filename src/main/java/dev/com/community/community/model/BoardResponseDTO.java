package dev.com.community.community.model;

import dev.com.community.community.entity.BoardEntity;
import lombok.Getter;

@Getter
public class BoardResponseDTO {
    private final String title;
    private final String content;

    public BoardResponseDTO(BoardEntity boardEntity){
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
    }
}
