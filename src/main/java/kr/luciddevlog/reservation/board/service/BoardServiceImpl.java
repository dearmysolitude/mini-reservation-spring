package kr.luciddevlog.reservation.board.service;

import kr.luciddevlog.reservation.board.dto.BoardForm;
import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.dto.Pagination;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import kr.luciddevlog.reservation.board.exception.BoardRequestFailException;
import kr.luciddevlog.reservation.board.repository.BoardItemRepository;
import kr.luciddevlog.reservation.board.repository.CommentRepository;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.repository.UserItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
	private final BoardItemRepository boardItemRepository;
	private final UserItemRepository userItemRepository;
	private final CommentRepository commentRepository;
	private static final int PAGE_UNIT = 10;
	private static final String ID_NOT_FOUND = "해당 게시글을 찾을 수 없습니다.";
	private static final int MAX_TITLE_LENGTH = 70;
	private static final int MAX_CONTENT_LENGTH = 500;
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	@Autowired
	public BoardServiceImpl(BoardItemRepository boardItemRepository, UserItemRepository userItemRepository, CommentRepository commentRepository) {
		this.boardItemRepository = boardItemRepository;
		this.userItemRepository = userItemRepository;
		this.commentRepository = commentRepository;
	}

	private Integer countTotalPage(BoardCategory boardCategory, int sizePerPage) {
		Long totalItems = null;
		totalItems = getTotalItems(boardCategory);
		return (int) (Math.ceil((float)totalItems / (float)sizePerPage));
	}

	private Long getTotalItems(BoardCategory boardCategory) {
		Long totalItems;
		if(boardCategory !=BoardCategory.NOTICE) {
			totalItems = boardItemRepository.countByCategory(boardCategory);
		} else {
			totalItems = boardItemRepository.countByCategoryAndRootIdIsNull(boardCategory);
		}
		return totalItems;
	}

	public Page<BoardItemWithAuthorName> searchContents(BoardCategory boardCategory, int sizePerPage, int currentPage) {
		int totalPage = countTotalPage(boardCategory, sizePerPage);
		// currentPage가 1보다 작거나 최대 크기보다 큰 경우에 대한 처리가 필요함.
		if(currentPage < 1) currentPage = 1;
		if(currentPage > totalPage && totalPage != 0) currentPage = totalPage;

		// 0-based index로 변환
		int pageIndex = currentPage - 1;

		PageRequest pageRequest = PageRequest.of(pageIndex, sizePerPage);

		return boardItemRepository.findBoardItemsByCategory(boardCategory, pageRequest);
	}

	public BoardItemWithAuthorName showSingleContent(Long id) {
        return boardItemRepository.findBoardItemById(id);
    }
	 
	/* 테스트케이스
	1. 10 -1 -> 0 0 11 53  // 0 값은 출력하지 않을 때 만든다.
	2. 10 0  -> 0 0 11 53
	3. 10 1  -> 0 0 11 53
	4. 10 5  -> 0 0 15 53
	5. 10 9  -> 0 0 19 53
	6. 10 10 -> 0 0 20 53
	7. 10 11 -> 0 0 21 53
	8. 10 49 -> 1 39 53 53
	9. 10 50 -> 1 40 0 0
	10. 10 51 -> 1 41 0 0
	*/
	public Pagination makePageInfo(BoardCategory boardCategory, int sizePerPage, int currentPage) {
		int totalPage = countTotalPage(boardCategory, sizePerPage);
		// 위 결과에 <<, <, >, >> 에 들어갈 index를 더해야 한다: 0값은 view 단에서 출력하지 않을 것을 나타냄.
		List<Integer> pageInfo = makePages(currentPage, totalPage);

		List<Integer> prev = frontPage(currentPage, totalPage);
		List<Integer> rear = rearPage(currentPage, totalPage);
		Long totalItems = getTotalItems(boardCategory);

		return Pagination.builder()
				.totalRecordCount(totalItems)
				.pp(prev.get(0))
				.p(prev.get(1))
				.n(rear.get(0))
				.nn(rear.get(1))
				.totalPageCount(totalPage)
				.pageList(pageInfo)
				.build();
	}

	private List<Integer> frontPage(int currentPage, int totalPage) {
		if(totalPage <= PAGE_UNIT || currentPage <= PAGE_UNIT) {
			return List.of(0, 0);
		}
		return List.of(1, currentPage - PAGE_UNIT);
	}
	private List<Integer> rearPage(int currentPage, int totalPage) {
		if(currentPage <= 0) {
			currentPage = 1;
		}
		if(currentPage > totalPage) {
			currentPage = totalPage;
		}

		if(currentPage != ((totalPage / PAGE_UNIT) * PAGE_UNIT) && (totalPage / PAGE_UNIT) == (currentPage / PAGE_UNIT)) {
			return List.of(0, 0);
		}

		int nextPage = ((currentPage / PAGE_UNIT) + 1) * PAGE_UNIT + (currentPage % PAGE_UNIT);
		if(nextPage > totalPage) {
			nextPage = totalPage;
		}
		return List.of(nextPage, totalPage);
	}

	private List<Integer> makePages(int currentPage, int totalPage) {
		if(totalPage == 0) {
			totalPage = 1;
		}

		if(currentPage <= 0) {
			currentPage = 1;
		}
		if(currentPage > totalPage) {
			currentPage = totalPage;
		}

								 // 보여줄 페이지 목록
		ArrayList<Integer> pages = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	ArrayList<Integer> retPages = new ArrayList<>();
	int pageRange = (currentPage / PAGE_UNIT) * PAGE_UNIT;
		if((currentPage % PAGE_UNIT == 0)) {
		pageRange -= PAGE_UNIT;
	}
		for(int i = 0; i < pages.size(); i++) {
		if(pages.get(i) + pageRange > totalPage) {
			break;
		}
		retPages.add(i, pages.get(i) + pageRange);
	}
		return retPages;
}

public BoardItem createBoard(BoardForm boardForm, Long memberId) {
		String title = boardForm.getTitle();
		String content = boardForm.getContent();
		UserItem member = userItemRepository.findById(memberId).orElse(null);

		if(member == null) {
			throw new IllegalArgumentException("잘못된 유저 id가 입력됨");
		}

		if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

		if(content.length() > MAX_CONTENT_LENGTH || title.length() > MAX_TITLE_LENGTH) {
			throw new IllegalArgumentException("실패: 내용/제목이 너무 김");
		}

		BoardItem board = BoardItem.builder()
				.title(boardForm.getTitle())
				.content(boardForm.getContent())
				.writer(member)
				.rootId(boardForm.getRootId())
				.build();
		return boardItemRepository.save(board);
	}

	public void updateBoardItem(Long id, BoardForm boardForm) {
		BoardItem board = boardItemRepository.findById(id).orElse(null);

		if(board == null) {
			throw new IllegalArgumentException(ID_NOT_FOUND);
		}
		boardItemRepository.save(board.patch(boardForm));
	}

	@Transactional
	public void deleteBoardItem(Long id) {
		BoardItem board = boardItemRepository.findById(id).orElse(null);
		if(board == null) {
			throw new IllegalArgumentException(ID_NOT_FOUND);
		}
		commentRepository.deleteByRootId(board.getId());
		boardItemRepository.deleteById(id);
	}

	@Transactional
	public void updateViewCount(Long id) {
		if(boardItemRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException(ID_NOT_FOUND);
		}

		try {
			boardItemRepository.incrementViewCount(id);
		} catch (Exception e) {
            logger.error("조회수 업데이트 실패: {}", e.getMessage(), e);
			throw new BoardRequestFailException("조회수 업데이트 실패");
		}
	}
	
	public BoardItem getBoardItem(Long id) {
		if(boardItemRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException(ID_NOT_FOUND);
		}
        return boardItemRepository.findById(id).orElse(null);
	}
}
