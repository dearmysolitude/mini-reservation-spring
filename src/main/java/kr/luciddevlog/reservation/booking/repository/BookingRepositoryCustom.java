package kr.luciddevlog.reservation.booking.repository;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepositoryCustom {
    long verifyBookingByDateAndRoomId(LocalDate localDate, Long roomId);
    Optional<LocalDate> findFastestCheckInDateByRoomId(Long roomId, LocalDate currentDate);
}
