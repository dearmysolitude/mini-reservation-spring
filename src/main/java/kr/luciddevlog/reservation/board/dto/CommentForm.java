package kr.luciddevlog.reservation.board.dto;

import kr.luciddevlog.reservation.user.dto.MemberInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CommentForm {
    private String content;
    private int reLevel;
    private Long rootId; // 댓글이 달리느 ㄴ게시글
    private Long memberId; // 게시글을 다는 멤버의 아이디
    private Long id; // 원본 댓글

    public void addMember(Long id) {
        this.memberId = id;
    }
}
