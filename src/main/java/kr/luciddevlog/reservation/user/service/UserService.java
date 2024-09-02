package kr.luciddevlog.reservation.user.service;

import kr.luciddevlog.reservation.user.dto.MemberInfo;
import kr.luciddevlog.reservation.user.dto.RegisterForm;

public interface UserService {
    void register(RegisterForm form);
    MemberInfo makeMemberInfo(Long id);
}
