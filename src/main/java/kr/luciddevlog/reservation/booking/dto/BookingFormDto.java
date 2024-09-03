package kr.luciddevlog.reservation.booking.dto;

import java.time.LocalDate;

public class BookingFormDto {
    private Long roomId;
    private String memo;
    private String userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer people;
}
