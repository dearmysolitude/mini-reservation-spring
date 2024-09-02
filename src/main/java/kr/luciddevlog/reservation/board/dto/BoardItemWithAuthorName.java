package kr.luciddevlog.reservation.board.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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
    private String category;

    @Builder
    public BoardItemWithAuthorName(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt,
                                   Integer reLevel, Integer reCnt, Long rootId, Integer viewCnt, String writerName, BoardCategory category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reLevel = reLevel;
        this.reCnt = reCnt;
        this.rootId = rootId;
        this.viewCnt = viewCnt;
        this.writerName = writerName;
        switch (category) {
            case NOTICE:
                this.category = "notice";
                break;
            case REVIEW:
                this.category = "review";
                break;
        }
    }
}
