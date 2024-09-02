package kr.luciddevlog.reservation.user.service;

import kr.luciddevlog.reservation.user.dto.RegisterForm;

public interface UserService {
    void register(RegisterForm form);
}
