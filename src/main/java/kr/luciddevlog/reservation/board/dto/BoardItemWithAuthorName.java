package kr.luciddevlog.reservation.board.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardItemWithAuthorName {
    private Long id;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Integer reLevel;
    private Integer reCnt;
    private Long rootId;
    private Integer viewCnt;
    private Long writerId;
    private String writerName;
    private BoardCategory category;

    @Builder
    public BoardItemWithAuthorName(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt,
                                   Integer reLevel, Integer reCnt, Long rootId, Integer viewCnt, Long writerId, String writerName, BoardCategory category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt.toLocalDate();
        this.updatedAt = updatedAt.toLocalDate();
        this.reLevel = reLevel;
        this.reCnt = reCnt;
        this.rootId = rootId;
        this.viewCnt = viewCnt;
        this.writerId = writerId;
        this.writerName = writerName;
        this.category = category;
    }

    public BoardItemWithAuthorName(Long id, String title, String content, LocalDate createdAt, LocalDate updatedAt,
                                   Integer reLevel, Integer reCnt, Long rootId, Integer viewCnt, Long writerId, String writerName, BoardCategory category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reLevel = reLevel;
        this.reCnt = reCnt;
        this.rootId = rootId;
        this.viewCnt = viewCnt;
        this.writerId = writerId;
        this.writerName = writerName;
        this.category = category;
    }


    public boolean isNotice() {
        return this.category == BoardCategory.NOTICE;
    }
}
