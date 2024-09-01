package kr.luciddevlog.reservation.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private String title;
    private String content;
    private Long boardId;
}
