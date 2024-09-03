package kr.luciddevlog.reservation.booking.service;

import kr.luciddevlog.reservation.booking.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<RoomDailyStatusDto> showMonthlySchedule(LocalDate from);
    RoomsAndMemberDto deliverBookingForm(Long roomId);
    boolean makeReservation(BookingFormDto form);

    List<BookingInfoDto> bookingInfoListByMemberId(Long memberId);
    BookingInfoDto showBookingInfo(Long reservationId);
}
