package kr.luciddevlog.reservation.user.exception;

public class InvalidCredentialsException extends LoginException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
