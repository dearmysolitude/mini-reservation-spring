package kr.luciddevlog.reservation.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.luciddevlog.reservation.board.dto.BoardForm;
import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.dto.CommentForm;
import kr.luciddevlog.reservation.board.dto.Pagination;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import kr.luciddevlog.reservation.board.exception.BoardRequestFailException;
import kr.luciddevlog.reservation.board.service.BoardService;
import kr.luciddevlog.reservation.board.service.CommentService;
import kr.luciddevlog.reservation.user.entity.CustomUserDetails;
import kr.luciddevlog.reservation.user.entity.UserItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @GetMapping("{category}/list")
    public String showList(Model model,@PathVariable String category, @RequestParam(name="unit", defaultValue = "10") int pageSize,
                           @RequestParam(name="cur", defaultValue = "1") int page, HttpServletRequest request) {
        BoardCategory whatCategory = null;
        if(category.equals("notice")) {
            whatCategory = BoardCategory.NOTICE;
        } else {
            whatCategory = BoardCategory.REVIEW;
        }
        System.out.println("현재 default page: "+ page);
        Page<BoardItemWithAuthorName> boardList = boardService.searchContents(whatCategory, pageSize, page);
        Pagination pageInfo = boardService.makePageInfo(whatCategory, pageSize, page);

        model.addAttribute("BoardItem", boardList);
        model.addAttribute("totalItems", pageInfo.getTotalRecordCount());
        model.addAttribute("showPrevPrevPage", pageInfo.getPp() != 0);
        model.addAttribute("prevPrevPageUrl", calculatePageUrl(pageSize, pageInfo.getPp()));
        model.addAttribute("showPrevPage", pageInfo.getP() != 0);
        model.addAttribute("prevPageUrl", calculatePageUrl(pageSize, pageInfo.getP()));

        List<Map<String, Object>> pages = new ArrayList<>();
        List<Integer> pagesInt = pageInfo.getPageList();
        for (Integer thisPage : pagesInt) {
            Map<String, Object> pageList = new HashMap<>();
            pageList.put("number", thisPage);
            pageList.put("url", calculatePageUrl(pageSize, thisPage));
            pageList.put("isCurrentPage", page == thisPage);
            pages.add(pageList);
        }
        model.addAttribute("pages", pages);
        model.addAttribute("BoardName", whatCategory);
        model.addAttribute("showNextPage", pageInfo.getN() != 0);
        model.addAttribute("nextPageUrl", calculatePageUrl(pageSize, pageInfo.getN()));
        model.addAttribute("showNextNextPage", pageInfo.getNn() != 0);
        model.addAttribute("nextNextPageUrl", calculatePageUrl(pageSize, pageInfo.getNn()));

        // 현재 목록 페이지 URL을 세션에 저장
        request.getSession().setAttribute("boardListUrl", request.getRequestURL().toString() + "?" + request.getQueryString());
        model.addAttribute("category", category);

        return "board/list";
    }

    private String calculatePageUrl(int pageSize, int inputPage) {
        String baseURL = String.format("list?unit=%s&", pageSize);
        return baseURL + "cur=" + inputPage;
    }

    @GetMapping("{id}")
    public String getSingleBoard(@PathVariable Long id, Model model, HttpServletRequest request,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = null;
        if (userDetails != null) {
            userId = userDetails.getUserItem().getId();
        }

        BoardItemWithAuthorName b = boardService.showSingleContent(id, userId);
        model.addAttribute("boardItem", b);
        boardService.updateViewCount(id);

        if(!(b.getCategory() == (BoardCategory.NOTICE))) {
            List<BoardItem> comments = commentService.showContent(id);
            model.addAttribute("commentItems", comments);
        }
        // 세션에서 목록 페이지 URL 가져오기
        String listUrl = (String) request.getSession().getAttribute("boardListUrl");
        model.addAttribute("listUrl", listUrl != null ? listUrl : "/reservation/board/notice/list");
        return "board/view";
    }

    @PostMapping("write")
    public String writeBoardItem(@ModelAttribute BoardForm insertForm, @AuthenticationPrincipal CustomUserDetails userDetails,
                                 HttpServletRequest request) {
        UserItem userItem = userDetails.getUserItem();

        BoardItem board = boardService.createBoard(insertForm, userItem.getId());

        if(board == null) {
            throw new BoardRequestFailException("요청 실패: 새 글이 업데이트 되지 않음");
        }

        // 리디렉션 주소
        BoardCategory category = board.getCategory();

        return "redirect:/board/" + category.name().toLowerCase() + "/list";
    }

    @GetMapping("{category}/insert")
    public String toFormPage(@ModelAttribute BoardForm insertForm, @PathVariable String category, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserItem userItem = userDetails.getUserItem();

        insertForm.addAuthor(userItem);
        insertForm.setCategory(category);
        model.addAttribute("insertForm", insertForm);
        return "board/insert";
    }

    @GetMapping("{category}/update")
    public String toUpdateForm(@RequestParam("id") Long id, @PathVariable String category, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        BoardItem item = boardService.getBoardItem(id);

        if (item == null) {
            throw new BoardRequestFailException("해당 게시글을 찾을 수 없습니다.");
        }

        if (userDetails.getUserItem().isAdmin() && !Objects.equals(userDetails.getUserItem().getId(), item.getWriter().getId())) {
            throw new AccessDeniedException("관리자 혹은 작성자만 수정할 수 있습니다.");
        }
        model.addAttribute("boardItem", item);
        return "board/update";
    }

    @PostMapping("{category}/update/{id}")
    public String updateBoardItem(@ModelAttribute BoardForm updateForm, @PathVariable String category, @PathVariable Long id, Model model) {
        boardService.updateBoardItem(id, updateForm);
        return "redirect:/board/" + id + "?updated=true";
    }

    @GetMapping("{category}/delete/{id}")
    public String delete(@PathVariable Long id, Model model, @PathVariable String category, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || userDetails.getUserItem() == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }
        UserItem userItem = userDetails.getUserItem();
        if(userDetails.getUserItem().isAdmin() && !Objects.equals(userItem.getId(), boardService.getBoardItem(id).getWriter().getId())) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
        
        boardService.deleteBoardItem(id);
        return "redirect:/board/" + category + "/list";
    }

    @PostMapping("{boardId}/comment")
    public String addComment(@ModelAttribute CommentForm commentForm,
                             @PathVariable("boardId") Long boardId,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || userDetails.getUserItem() == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        UserItem userItem = userDetails.getUserItem();

        commentForm.addMember(userItem.getId());
        commentService.createComment(commentForm);
        return String.format("redirect:/board/%d", boardId);
    }

    @PostMapping("comment/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || userDetails.getUserItem() == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        UserItem userItem = userDetails.getUserItem();

        if(userDetails.getUserItem().isAdmin() && !Objects.equals(userItem.getId(), commentService.getCommentInfo(commentId).getWriter().getId())) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }

        Long boardId = commentService.deleteCommentItem(commentId);
        return String.format("redirect:/board/%d", boardId);
    }

    @PostMapping("comment/update/{commentId}")
    public String updateComment(@PathVariable("commentId") Long commentId,
                                @RequestParam("content") String content,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || userDetails.getUserItem() == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        UserItem userItem = userDetails.getUserItem();

        if(userDetails.getUserItem().isAdmin() && !Objects.equals(userItem.getId(), commentService.getCommentInfo(commentId).getWriter().getId())) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        Long boardId = commentService.updateCommentItem(commentId, content);
        return String.format("redirect:/board/%d", boardId);
    }

}
