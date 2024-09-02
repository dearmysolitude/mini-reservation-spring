package kr.luciddevlog.reservation.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.luciddevlog.reservation.board.dto.BoardForm;
import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.dto.CommentForm;
import kr.luciddevlog.reservation.board.dto.Pagination;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import kr.luciddevlog.reservation.board.exception.BoardRequestFailException;
import kr.luciddevlog.reservation.board.service.BoardService;
import kr.luciddevlog.reservation.board.service.CommentService;
import kr.luciddevlog.reservation.user.dto.MemberInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String showList(Model model,@PathVariable String category, @RequestParam(name="unit", defaultValue = "10") int pageSize, @RequestParam(name="cur", defaultValue = "1") int page) {
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

        return "board/list";
    }

    private String calculatePageUrl(int pageSize, int inputPage) {
        String baseURL = String.format("list?unit=%s&", pageSize);
        return baseURL + "cur=" + inputPage;
    }

    @GetMapping("{id}")
    public String getSingleBoard(@PathVariable Long id, Model model) {
        BoardItemWithAuthorName b = boardService.showSingleContent(id);
        model.addAttribute("boardItem", b);
        boardService.updateViewCount(id);

        List<BoardItem> comments = commentService.showContent(id);
        model.addAttribute("comments", comments);
        return "board/view";
    }

    @PostMapping("write")
    public String writeBoardItem(@ModelAttribute BoardForm insertForm, HttpServletRequest request) {
        // 세션 정보 가져와야.
        HttpSession session = request.getSession(false);
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("loggedInMember");

        BoardItem board = boardService.createBoard(insertForm, memberInfo.getId());
        if(board == null) {
            throw new BoardRequestFailException("요청 실패: 새 글이 업데이트 되지 않음");
        }
        return "redirect:/board/list";
    }

    @GetMapping("insert")
    public String toFormPage(@ModelAttribute BoardForm insertForm) {
        return "board/insert";
    }

    @GetMapping("update")
    public String toUpdateForm(@RequestParam("id") Long id, Model model) {
        BoardItem item = boardService.getBoardItem(id);
        model.addAttribute("boardItem", item);
        return "board/update";
    }

    @PostMapping("update/{id}")
    public String updateBoardItem(@ModelAttribute BoardForm updateForm, @PathVariable Long id, Model model) {
        boardService.updateBoardItem(id, updateForm);
        return String.format("redirect:/board/%d", id);
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        boardService.deleteBoardItem(id);
        return "redirect:/board/list";
    }

//    @GetMapping("/reInsertForm")
//    public String toReInsertForm(@RequestParam("id") Long id, Model model) {
//        Board item = boardService.getBoardItem(id);
//        model.addAttribute("boardItem", item);
//        return "board/reinsert";
//    }

    @PostMapping("{boardId}/comment")
    public String addComment(@ModelAttribute CommentForm commentForm,
                             @PathVariable("boardId") Long boardId,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("loggedInMember");

        commentForm.addMember(memberInfo);
        commentService.createComment(commentForm);
        return String.format("redirect:/board/%d", boardId);
    }

    @PostMapping("comment/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId, HttpServletRequest request) {
        // 세션 정보 가져와야.
        HttpSession session = request.getSession(false);
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("loggedInMember");
        Long boardId = commentService.deleteCommentItem(commentId);
        return String.format("redirect:/board/%d", boardId);
    }

    @PostMapping("comment/update/{commentId}")
    public String updateComment(@PathVariable("commentId") Long commentId,
                                @RequestParam("content") String content,
                                HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("loggedInMember");

        Long boardId = commentService.updateCommentItem(commentId, content);
        return String.format("redirect:/board/%d", boardId);
    }

}
