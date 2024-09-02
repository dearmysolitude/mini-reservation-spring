package kr.luciddevlog.reservation.board.repository;

import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardItemRepositoryCustom {
    Page<BoardItemWithAuthorName> findBoardItemsByCategory(BoardCategory category, Pageable pageable);
    BoardItemWithAuthorName findBoardItemById(Long id);
}
