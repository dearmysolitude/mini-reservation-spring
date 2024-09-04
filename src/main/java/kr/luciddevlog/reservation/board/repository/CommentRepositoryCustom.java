package kr.luciddevlog.reservation.board.repository;

import kr.luciddevlog.reservation.board.dto.CommentItemWithAuthorName;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentItemWithAuthorName> findCommentItemsByRootId(Long rootId);
}
