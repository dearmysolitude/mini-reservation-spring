package kr.luciddevlog.reservation.booking.dto;

import kr.luciddevlog.reservation.booking.entity.RoomItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RoomInfoDto {
    // 가격, 설명, 이름
    private Long roomId;
    private Integer price;
    private String description;
    private String name;

    public static RoomInfoDto of(RoomItem roomItem) {
        return RoomInfoDto.builder()
                .roomId(roomItem.getId())
                .price(roomItem.getPrice())
                .description(roomItem.getDescription())
                .name(roomItem.getName())
                .build();
    }
}
