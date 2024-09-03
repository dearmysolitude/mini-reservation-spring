package kr.luciddevlog.reservation.booking.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static kr.luciddevlog.reservation.booking.entity.QRoomItem.roomItem;
import static kr.luciddevlog.reservation.booking.entity.QBookingItem.bookingItem;

import java.time.LocalDate;

public class BookingRepositoryCustomImpl implements BookingRepositoryCustom{
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    public BookingRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public long verifyBookingByDateAndRoomId(LocalDate localDate, Long roomId) {
        return jpaQueryFactory.selectFrom(bookingItem)
                    .where(bookingItem.roomItem.id.eq(roomId)
                            .and(bookingItem.checkInDate.loe(localDate))
                            .and(bookingItem.checkOutDate.gt(localDate)))
                    .fetch()
                .size();
    }
}
