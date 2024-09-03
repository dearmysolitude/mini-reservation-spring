package kr.luciddevlog.reservation.board.dto;

public class CommentDto extends CommentItemWithAuthorName{
    private boolean mine;
    public CommentDto(CommentItemWithAuthorName commentItemWithAuthorName, Long userId) {
        super(commentItemWithAuthorName.getId(),
                commentItemWithAuthorName.getContent(),
                commentItemWithAuthorName.getCreatedAt(),
                commentItemWithAuthorName.getUpdatedAt(),
                commentItemWithAuthorName.getReLevel(),
                commentItemWithAuthorName.getReCnt(),
                commentItemWithAuthorName.getRootId(),
                commentItemWithAuthorName.getViewCnt(),
                commentItemWithAuthorName.getWriterId(),
                commentItemWithAuthorName.getWriterName(),
                commentItemWithAuthorName.getCategory());

        this.mine = userId != null && userId.equals(commentItemWithAuthorName.getWriterId());
    }
}
