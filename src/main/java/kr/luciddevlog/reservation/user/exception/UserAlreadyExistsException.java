package kr.luciddevlog.reservation.user.exception;

public class UserAlreadyExistsException extends LoginException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
