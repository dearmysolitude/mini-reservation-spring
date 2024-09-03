package kr.luciddevlog.reservation.booking.repository;

import java.time.LocalDate;

public interface BookingRepositoryCustom {
    long verifyBookingByDateAndRoomId(LocalDate localDate, Long roomId);
}
