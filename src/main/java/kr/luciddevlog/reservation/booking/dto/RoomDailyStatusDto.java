package kr.luciddevlog.reservation.booking.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RoomDailyStatusDto {
    private LocalDate date;
    @Setter
    private List<RoomStatusDto> rooms;

    public RoomDailyStatusDto(LocalDate date) {
        this.date = date;
        this.rooms = new ArrayList<>();
    }
    public void addRoomStatus(RoomStatusDto room) {
        rooms.add(room);
    }
}
