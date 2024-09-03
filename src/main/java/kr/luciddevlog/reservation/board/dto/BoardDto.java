package kr.luciddevlog.reservation.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto extends BoardItemWithAuthorName{
    private boolean mine;

    public BoardDto(BoardItemWithAuthorName boardItemWithAuthorName, Long userId) {
        super(boardItemWithAuthorName.getId(),
                boardItemWithAuthorName.getTitle(),
                boardItemWithAuthorName.getContent(),
                boardItemWithAuthorName.getCreatedAt(),
                boardItemWithAuthorName.getUpdatedAt(),
                boardItemWithAuthorName.getReLevel(),
                boardItemWithAuthorName.getReCnt(),
                boardItemWithAuthorName.getRootId(),
                boardItemWithAuthorName.getViewCnt(),
                boardItemWithAuthorName.getWriterId(),
                boardItemWithAuthorName.getWriterName(),
                boardItemWithAuthorName.getCategory());

        this.mine = userId != null && userId.equals(boardItemWithAuthorName.getWriterId());
    }
}
