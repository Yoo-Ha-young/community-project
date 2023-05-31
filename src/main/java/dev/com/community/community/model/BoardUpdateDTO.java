package dev.com.community.community.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardUpdateDTO {
    private String title;
    private String content;
    private LocalDateTime updatedAt;
}
