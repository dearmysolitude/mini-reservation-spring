package kr.luciddevlog.reservation.booking.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PriceRequestDto {
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;


}
