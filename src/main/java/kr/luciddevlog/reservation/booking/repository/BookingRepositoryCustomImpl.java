package kr.luciddevlog.reservation.booking.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static kr.luciddevlog.reservation.booking.entity.QBookingItem.bookingItem;

import java.time.LocalDate;
import java.util.Optional;

public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {
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

    public Optional<LocalDate> findFastestCheckInDateByRoomId(Long roomId, LocalDate currentDate) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(bookingItem.checkInDate)
                        .from(bookingItem)
                        .where(
                                roomIdEquals(roomId),
                                dateIsAfterOrEqual(currentDate)
                        )
                        .orderBy(bookingItem.checkInDate.asc())
                        .fetchFirst()
        );
    }


    private BooleanExpression roomIdEquals(Long roomId) {
        return roomId != null ? bookingItem.roomItem.id.eq(roomId) : null;
    }

    private BooleanExpression dateIsAfterOrEqual(LocalDate date) {
        return date != null ? bookingItem.checkInDate.goe(date) : null;
    }

}
