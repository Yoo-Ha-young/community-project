package dev.com.community.community.model;

import dev.com.community.community.entity.BoardEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
public class BoardResponseDTO {
    private Long userId;
    private String username;

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static BoardResponseDTO of(BoardEntity boardEntity) {
        return BoardResponseDTO.builder()
            .userId(boardEntity.getId())
            .title(boardEntity.getTitle())
            .content(boardEntity.getContent())
            .createdAt(boardEntity.getCreatedAt())
            .updatedAt(boardEntity.getUpdatedAt())
            .userId(boardEntity.getUserEntity().getId())
            .username(boardEntity.getUserEntity().getUsername())
            .build();
    }
}
