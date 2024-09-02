package kr.luciddevlog.reservation.board.dto;

import jakarta.validation.constraints.NotBlank;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import kr.luciddevlog.reservation.user.entity.UserItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BoardForm {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private UserItem member;
    private Long rootId;

    public BoardItem toEntity() {
        return BoardItem.builder()
                .title(this.title)
                .rootId(this.rootId)
                .content(this.content)
                .writer(this.member)
                .build();
    }

    public void addAuthor(UserItem member) {
        this.member = member;
    }
    
}
