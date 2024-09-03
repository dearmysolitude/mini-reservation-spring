package kr.luciddevlog.reservation.booking.service;

import kr.luciddevlog.reservation.booking.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<RoomDailyStatusDto> makeMonthlySchedule();
    RoomsAndMemberDto deliverBookingForm();
    boolean makeReservation(BookingFormDto form);

//    List<BookingInfoDto> bookingInfoListByMemberId(Long memberId);
//    BookingInfoDto showBookingInfo(Long reservationId);
}
