package kr.luciddevlog.reservation.user.dto;

import kr.luciddevlog.reservation.user.entity.UserItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String phoneNumber;

    public static UserDto of(UserItem userItem) {
        return new UserDto(userItem.getId(), userItem.getUsername(), userItem.getPhoneNumber());
    }
}
