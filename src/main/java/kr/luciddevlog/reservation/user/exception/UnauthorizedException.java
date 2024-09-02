package kr.luciddevlog.reservation.user.exception;

import java.nio.file.AccessDeniedException;

public class UnauthorizedException extends AccessDeniedException {
    public UnauthorizedException(String s) {
        super(s);
    }
}
