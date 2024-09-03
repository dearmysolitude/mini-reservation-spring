package kr.luciddevlog.reservation.booking.dto;

public class RoomStatusDto {
    private boolean available;
    private RoomInfoDto roomInfoDto;
    public RoomStatusDto(RoomInfoDto roomInfoDto, boolean available) {
        this.available = available;
        this.roomInfoDto = roomInfoDto;
    }
}
