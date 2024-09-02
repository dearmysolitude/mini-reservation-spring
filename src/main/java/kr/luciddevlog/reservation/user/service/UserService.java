package kr.luciddevlog.reservation.user.service;

import kr.luciddevlog.reservation.user.dto.LoginForm;
import kr.luciddevlog.reservation.user.dto.MemberInfo;
import kr.luciddevlog.reservation.user.dto.RegisterForm;
import kr.luciddevlog.reservation.user.entity.UserItem;

public interface UserService {
    UserItem login(LoginForm form);
    void register(RegisterForm form);
    MemberInfo makeMemberInfo(Long id);
}
