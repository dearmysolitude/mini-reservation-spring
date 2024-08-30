package kr.luciddevlog.reservation.user.dto;

import kr.luciddevlog.reservation.user.entity.UserItem;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String name;

    public UserItem toEntity() {
        return UserItem.builder().build();
    }

    // 나중에 손볼지도
    public static UserDto of(Long id, String username, String name) {
        return UserDto.builder()
                .id(id)
                .username(username)
                .name(name)
                .build();
    }
}
