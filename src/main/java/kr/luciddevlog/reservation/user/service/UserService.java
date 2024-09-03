package kr.luciddevlog.reservation.user.service;

import kr.luciddevlog.reservation.user.dto.RegisterFormDto;

public interface UserService {
    void register(RegisterFormDto form);
}
