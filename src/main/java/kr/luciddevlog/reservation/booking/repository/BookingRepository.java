package kr.luciddevlog.reservation.booking.repository;

import kr.luciddevlog.reservation.booking.entity.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<BookingItem, Long> , BookingRepositoryCustom{
    long countByRoomItemIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThan(
            Long roomId, LocalDate bookingDate, LocalDate bookingDatePlusOne);
}
