package kr.luciddevlog.reservation.board.service;


import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.dto.CommentForm;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    List<BoardItem> showContent(Long rootId);
    // 댓글 crud
    void createComment(CommentForm commentForm);
    Long updateCommentItem(Long id, String content);
    Long deleteCommentItem(Long id);
    BoardItem getCommentInfo(Long id);
}
