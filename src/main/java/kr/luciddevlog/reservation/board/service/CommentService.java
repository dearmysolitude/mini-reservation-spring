package kr.luciddevlog.reservation.board.service;


import kr.luciddevlog.reservation.board.dto.CommentForm;
import kr.luciddevlog.reservation.board.dto.CommentItemWithAuthorName;
import kr.luciddevlog.reservation.board.entity.BoardItem;

import java.util.List;

public interface CommentService {
    List<CommentItemWithAuthorName> showContent(Long rootId, Long userId);
    // 댓글 crud
    void createComment(CommentForm commentForm);
    Long updateCommentItem(Long id, String content);
    Long deleteCommentItem(Long id);
    BoardItem getCommentInfo(Long id);
}
