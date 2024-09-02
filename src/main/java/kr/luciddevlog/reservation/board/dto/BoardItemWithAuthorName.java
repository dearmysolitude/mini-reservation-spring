package kr.luciddevlog.reservation.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
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
    private int viewCnt;
    private String writer;
}
