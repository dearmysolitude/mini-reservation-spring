package kr.luciddevlog.reservation.booking.service;

import kr.luciddevlog.reservation.booking.dto.*;
import kr.luciddevlog.reservation.user.entity.UserItem;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<RoomDailyStatusDto> makeMonthlySchedule();
    RoomAndMemberDto deliverBookingForm(Long roomId, UserItem userItem);
    boolean makeReservation(BookingFormDto form);
    PriceDto makePriceResponse(PriceRequestDto priceRequestDto);
    List<RoomDailyStatusDto> getBookedDatesForRoom(Long roomId, LocalDate date);
    LocalDate getFastestBookedDateForRoom(Long roomId, LocalDate date);
//    List<BookingInfoDto> bookingInfoListByMemberId(Long memberId);
//    BookingInfoDto showBookingInfo(Long reservationId);
}
