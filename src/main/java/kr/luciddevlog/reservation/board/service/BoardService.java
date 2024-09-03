package kr.luciddevlog.reservation.board.service;

import kr.luciddevlog.reservation.board.dto.BoardForm;
import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.dto.Pagination;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import org.springframework.data.domain.Page;

public interface BoardService {
	// 페이지네이션
	Page<BoardItemWithAuthorName> searchContents(BoardCategory boardCategory, int sizePerPage, int currentPage);
	Pagination makePageInfo(BoardCategory boardCategory, int sizePerPage, int currentPage);

	// 게시글 crud
	BoardItemWithAuthorName showSingleContent(Long id, Long userId);
	BoardItem createBoard(BoardForm boardForm, Long memberId);
	void updateBoardItem(Long id, BoardForm form);
	void deleteBoardItem(Long id);

	// 댓글
	void updateViewCount(Long id);
	BoardItem getBoardItem(Long id);
}