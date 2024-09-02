package kr.luciddevlog.reservation.board.dto;

import jakarta.validation.constraints.NotBlank;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.repository.UserItemRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class BoardForm {
    @Autowired
    private UserItemRepository userItemRepository;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private Long memberId;
    private Long rootId;
    private BoardCategory category;

    @Builder
    public BoardForm(String title, String content, Long memberId, Long rootId, String category) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.rootId = rootId;
        addCategory(category);
    }

    public BoardItem toEntity() {
        return BoardItem    .builder()
                .title(this.title)
                .rootId(this.rootId)
                .content(this.content)
                .writer(userItemRepository.findById(this.memberId).orElseThrow())
                .category(this.category)
                .build();
    }

    public void addAuthor(UserItem member) {
        this.memberId = member.getId();
    }
    public void addCategory(String category) {
        if(category.equals("notice")) {
            this.category = BoardCategory.NOTICE;
        } else {
            this.category = BoardCategory.REVIEW;
        }
    }

    @Override
    public String toString() {
        return "BoardForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", memberId=" + memberId +
                ", rootId=" + rootId +
                ", category=" + category +
                '}';
    }

}
