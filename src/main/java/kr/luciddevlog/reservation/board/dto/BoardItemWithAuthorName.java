package kr.luciddevlog.reservation.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardItemWithAuthorName {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer reLevel;
    private Integer reCnt;
    private Long rootId;
    private Integer viewCnt;
    private String writerName;
}
