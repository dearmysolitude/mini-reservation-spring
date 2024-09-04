package kr.luciddevlog.reservation.booking.service;

import kr.luciddevlog.reservation.booking.dto.*;
import kr.luciddevlog.reservation.booking.entity.BookingItem;
import kr.luciddevlog.reservation.booking.entity.ReservationStatus;
import kr.luciddevlog.reservation.booking.repository.BookingRepository;
import kr.luciddevlog.reservation.booking.repository.RoomRepository;
import kr.luciddevlog.reservation.user.dto.UserDto;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
    BookingRepository bookingRepository;
    RoomRepository roomRepository;
    UserItemRepository userItemRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository, UserItemRepository userItemRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userItemRepository = userItemRepository;
    }

    public List<RoomDailyStatusDto> makeMonthlySchedule() {
        return makeMonthlySchedule(getRoomsInfo());
    }

    public List<RoomDailyStatusDto> makeMonthlySchedule(List<RoomInfoDto> rooms) {
        LocalDate today = LocalDate.now();
        LocalDate temp;
        List<RoomDailyStatusDto> monthlyStatus = new ArrayList<>();

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
                dailyStatus.addRoomStatus(new RoomStatusDto(room, true));
                continue;
            }
            dailyStatus.addRoomStatus(new RoomStatusDto(room, false));
        }
        return dailyStatus;
    }

    private List<RoomInfoDto> getRoomsInfo() {
        List<RoomInfoDto> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(
            roomItem -> rooms.add(RoomInfoDto.of(roomItem))
        );
        return rooms;
    }

    // 유저 정보는 컨트롤러에서
    public RoomAndMemberDto deliverBookingForm(Long roomId, UserItem userItem) {
        return new RoomAndMemberDto(UserDto.of(userItem), RoomInfoDto.of(roomRepository.findById(roomId).orElseThrow()));
    }

    public void makeReservation(BookingFormDto form) {
        BookingItem bookingItem = formToBookingItem(form);
        try {
            bookingRepository.save(bookingItem);
        } catch (Exception e) {
            // 로그 저장
            System.out.println(e.getMessage());
        }
    }

    private BookingItem formToBookingItem(BookingFormDto form) {
        return BookingItem.builder()
                .roomItem(roomRepository.findById(form.getRoomId()).orElseThrow())
                .user(userItemRepository.findById(form.getUserId()).orElseThrow())
                .memo(form.getMemo())
                .checkInDate(form.getCheckInDate())
                .checkOutDate(form.getCheckOutDate())
                .people(form.getPeople())
                .status(ReservationStatus.BEFORE_DEPOSIT)
                .build();
    }

    public PriceDto makePriceResponse(PriceRequestDto priceRequestDto) {
        long stayDuration = getStayDuration(priceRequestDto);
        BigDecimal totalPrice = getTotalPrice(priceRequestDto, stayDuration);
        return new PriceDto(stayDuration, totalPrice);
    }

    private static long getStayDuration(PriceRequestDto priceRequestDto) {
        if (priceRequestDto.getCheckOutDate().isBefore(priceRequestDto.getCheckInDate()) ||
                priceRequestDto.getCheckOutDate().isEqual(priceRequestDto.getCheckInDate())) {
            throw new IllegalArgumentException("체크아웃 날짜는 체크인 날짜 이후여야 합니다.");
        }

        return ChronoUnit.DAYS.between(priceRequestDto.getCheckInDate(), priceRequestDto.getCheckOutDate());
    }

    private BigDecimal getTotalPrice(PriceRequestDto priceRequestDto, long stayDuration) {
        int pricePerNight = roomRepository.findById(priceRequestDto.getRoomId()).orElseThrow().getPrice();
        return BigDecimal.valueOf(pricePerNight).multiply(BigDecimal.valueOf(stayDuration));
    }

    public List<RoomDailyStatusDto> getBookedDatesForRoom(Long roomId, LocalDate date) {
        return makeMonthlySchedule(List.of(RoomInfoDto.of(roomRepository.findById(roomId).orElseThrow())));
    }

    public LocalDate getFastestBookedDateForRoom(Long roomId, LocalDate date) {
        return bookingRepository.findFastestCheckInDateByRoomId(roomId, date).orElse(null);
    }


//    public List<BookingInfoDto> bookingInfoListByMemberId(Long memberId) {
//
//
//    }
//    BookingInfoDto showBookingInfo(Long reservationId);
}
