package kr.luciddevlog.reservation.user.dto;

import kr.luciddevlog.reservation.user.entity.UserItem;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberInfo {
    private final Long id;
    private final String userName;
    private final LocalDateTime createdAt;
    private final String name;
    private final String phoneNumber;

    public MemberInfo(UserItem member) {
        this.id = member.getId();
        this.name = member.getName();
        this.userName = member.getUsername();
        this.createdAt = member.getCreatedAt();
        this.phoneNumber = member.getPhoneNumber();
    }
}
