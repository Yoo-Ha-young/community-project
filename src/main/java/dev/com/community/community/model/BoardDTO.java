package dev.com.community.community.model;


import dev.com.community.community.entity.BoardEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardDTO {

    private String title;
    private String content;
    private LocalDateTime createdAt;

    public BoardEntity toEntity() {
        return BoardEntity.builder()
            .title(title)
            .content(content)
            .build();
    }
}
