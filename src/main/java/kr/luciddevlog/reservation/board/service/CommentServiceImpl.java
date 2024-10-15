package kr.luciddevlog.reservation.board.service;

import kr.luciddevlog.reservation.board.dto.CommentDto;
import kr.luciddevlog.reservation.board.dto.CommentForm;
import kr.luciddevlog.reservation.board.dto.CommentItemWithAuthorName;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import kr.luciddevlog.reservation.board.repository.CommentRepository;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final UserItemRepository memberRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserItemRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
    }

    // 대댓글에 대한 Indent를 전달할 dto필요 혹은 프론트에서 reLevel에 따라 들여써야 함
    public List<CommentItemWithAuthorName> showContent(Long rootId, Long userId) {
        List<CommentItemWithAuthorName> comments = commentRepository.findCommentItemsByRootId(rootId);

        if (userId == null) {
            return comments;
        }

        return comments.stream()
                .map(commentItemWithAuthorName -> new CommentDto(commentItemWithAuthorName, userId))
                .collect(Collectors.toList());
    }

    // 댓글의 순서를 부여하는 것이 주요 로직
    @Transactional
    public void createComment(CommentForm commentForm) {
        String content = commentForm.getContent();
        Long rootId = commentForm.getRootId();
        Long parentId = commentForm.getParentId();
        UserItem member = memberRepository.findById(commentForm.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않음")
        );

        if(content == null || rootId == null) {
            throw new IllegalArgumentException("인자가 잘못 전달됨");
        }

        if(parentId == -1) { // 새 코멘트 작성
            BoardItem comment = BoardItem.builder()
                    .rootId(rootId)
                    .category(BoardCategory.REVIEW)
                    .content(content)
                    .writer(member)
                    .reLevel(1)
                    .reCnt(commentRepository.countByRootId(rootId) + 1)
                    .build();

            commentRepository.save(comment);
            return;
        }
        createSubComment(parentId, content, member);
    }

    private void createSubComment(Long parentId, String content, UserItem member) { // 들어오는 id는 답글 달 댓글의 id
        BoardItem parentComment = commentRepository.findById(parentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다")
        );

        int myReCnt = parentComment.getReCnt();
        int myReLevel = parentComment.getReLevel();
        Long rootId = parentComment.getRootId();
        int index = decideIndex(rootId, myReLevel, myReCnt);

        commentRepository.updateReCntForComment(rootId, index);

        BoardItem newComment = BoardItem.builder()
                .category(BoardCategory.REVIEW)
                .content(content)
                .rootId(rootId)
                .reLevel(myReLevel + 1)
                .reCnt(index)
                .writer(member)
                .build();

        commentRepository.save(newComment);
    }

    private Integer decideIndex(Long rootId, int myReLevel, int myReCnt) {
        Integer index = commentRepository.findMinReCnt(rootId, myReLevel, myReCnt);

        if(index == null || index == -1 || index == 0) {
            Integer temp = commentRepository.findMaxReCnt(rootId, myReLevel + 1, myReCnt);

            if(temp == null || temp == -1 || temp == 0) {
                index = myReCnt + 1;
            } else {
                index = temp + 1;
            }
        }
        return index;
    }

    @Transactional
    public Long updateCommentItem(Long id, String content) {
        BoardItem comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다")
        );
        comment.patch(content);
        commentRepository.save(comment);
        return comment.getRootId();
    }

    @Transactional
    public Long deleteCommentItem(Long id) {
        BoardItem comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다")
        );

        Long boardId = comment.getRootId();

        // decrease comment recnt, rootid/recnt가지고.
        commentRepository.decreaseReCntForComment(comment.getRootId(), comment.getReCnt());
        commentRepository.delete(comment);

        return boardId;
    }

    public BoardItem getCommentInfo(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다")
        );
    }
}
