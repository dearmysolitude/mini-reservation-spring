package kr.luciddevlog.reservation.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.luciddevlog.reservation.board.dto.BoardForm;
import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.user.entity.UserItem;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BoardItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private UserItem writer;

    @Column(nullable = false, length = 3000)
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    // NOTICE 인 경우 null, REVIEW 중 본글은 null 댓글은 root 글을 참조함
    @Column(name = "root_id")
    private Long rootId;

    // BoardCategory == 1 이거나 reLevel == 0 이면 null
    @Column(name="view_cnt")
    private Integer viewCnt;

    @Column(nullable = false)
    private BoardCategory category;

    //  아래 필드들은 Comment에 대해서만 적용됨. BoardCategory: 1-NOTICE(아래 필드 모두 null), 2-REVIEW
    @Column(columnDefinition = "integer check (score between 1 and 5)")
    @Min(value = 1, message = "점수는 최소 1 이어야 합니다")
    @Max(value = 5, message = "점수는 최대 5 여야 합니다")
    private Integer score;

    @Column
    private String title; // root가 비어있는 경우 null 이면 안됨

    @Column(name="re_cnt") // root 빈 경우 re_cnt = 0
    private int reCnt;

    @Column(name="re_level") // root 빈 경우 re_level = 0
    private int reLevel;

    public void setRootId(BoardItem boardItem) {
        this.rootId = boardItem.rootId;
    }

    public BoardItemWithAuthorName toItemWithAuthorName() {
        return BoardItemWithAuthorName.builder()
                .content(this.content)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .writerName(this.writer.getName())
                .reCnt(this.reCnt)
                .reLevel(this.reLevel)
                .id(this.id)
                .rootId(this.rootId)
                .viewCnt(this.viewCnt)
                .title(this.title)
                .build();
    }

    public BoardItem patch(BoardForm boardForm) {
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
        return this;
    }

    public BoardItem patch(String content) {
        this.content = content;
        return this;
    }

    @PrePersist
    @PreUpdate
    private void validateFields() {
        if (category == BoardCategory.NOTICE) {
            rootId = null;
            score = null;
            reCnt = 0;
            reLevel = 0;
        } else { // BoardCategory.REVIEW인 경우
            if (rootId == null) { // 댓글이 아닌 경우는 RECNT=0, RELEVE=0이어야 함
                reCnt = 0;
                reLevel = 0;
            }
        }

        if(rootId == null) {
            if(title == null || title.isEmpty()) {
                throw new IllegalStateException("게시글은 제목이 비어있을 수 없습니다.");
            }
        }
    }


}
