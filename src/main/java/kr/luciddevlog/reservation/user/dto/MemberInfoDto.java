package kr.luciddevlog.reservation.user.dto;

import kr.luciddevlog.reservation.user.entity.UserItem;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberInfoDto {
    private final Long id;
    private final String userName;
    private final LocalDateTime createdAt;
    private final String name;
    private final String phoneNumber;

    public MemberInfoDto(UserItem member) {
        this.id = member.getId();
        this.name = member.getName();
        this.userName = member.getUsername();
        this.createdAt = member.getCreatedAt();
        this.phoneNumber = member.getPhoneNumber();
    }
}
