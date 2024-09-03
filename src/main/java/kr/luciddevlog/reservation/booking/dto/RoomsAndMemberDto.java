package kr.luciddevlog.reservation.booking.dto;

import kr.luciddevlog.reservation.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class RoomsAndMemberDto {
    private List<RoomInfoDto> rooms;
    @Setter
    private UserDto user;

    public RoomsAndMemberDto(List<RoomInfoDto> rooms) {
        this.rooms = rooms;
    }
}
