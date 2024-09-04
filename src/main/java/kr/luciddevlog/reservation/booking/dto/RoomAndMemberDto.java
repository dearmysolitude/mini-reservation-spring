package kr.luciddevlog.reservation.booking.dto;

import kr.luciddevlog.reservation.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RoomAndMemberDto {
    private final RoomInfoDto room;
    @Setter
    private UserDto user;

    public RoomAndMemberDto(UserDto user, RoomInfoDto room) {
        this.user = user;
        this.room = room;
    }
}
