package kr.luciddevlog.reservation.booking.service;

import kr.luciddevlog.reservation.booking.dto.*;
import kr.luciddevlog.reservation.booking.repository.BookingRepository;
import kr.luciddevlog.reservation.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
    BookingRepository bookingRepository;
    RoomRepository roomRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public List<RoomDailyStatusDto> makeMonthlySchedule() {
        LocalDate today = LocalDate.now();
        LocalDate temp;
        List<RoomDailyStatusDto> monthlyStatus = new ArrayList<>();

        List<RoomInfoDto> rooms = getRoomInfo();

        for(int i = 0; i < 30; i++) {
            temp = today.plusDays(i);
            RoomDailyStatusDto dailyStatus = getRoomDailyStatus(temp, rooms);
            monthlyStatus.add(dailyStatus);
        }

        return monthlyStatus;
    }

    private RoomDailyStatusDto getRoomDailyStatus(LocalDate temp, List<RoomInfoDto> rooms) {
        RoomDailyStatusDto dailyStatus = new RoomDailyStatusDto(temp);
        for(RoomInfoDto room : rooms) {
            if(bookingRepository.verifyBookingByDateAndRoomId(temp, room.getRoomId()) == 0) {
                continue;
            }
            dailyStatus.addRoomStatus(new RoomStatusDto(room, false));
        }
        return dailyStatus;
    }

    private List<RoomInfoDto> getRoomInfo() {
        List<RoomInfoDto> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(
            roomItem -> {
                rooms.add(RoomInfoDto.of(roomItem));
            }
        );
        return rooms;
    }

    public RoomsAndMemberDto deliverBookingForm() {
        return new RoomsAndMemberDto(getRoomInfo());
    }

    public boolean makeReservation(BookingFormDto form) {

    }

//
//    List<BookingInfoDto> bookingInfoListByMemberId(Long memberId);
//    BookingInfoDto showBookingInfo(Long reservationId);
}
