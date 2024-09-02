package kr.luciddevlog.reservation.board.exception;

public class BoardRequestFailException extends RuntimeException {
    public BoardRequestFailException(String message) {
        super(message);
    }
}
