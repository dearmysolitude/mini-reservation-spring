package kr.luciddevlog.reservation.booking.controller;

import kr.luciddevlog.reservation.booking.dto.*;
import kr.luciddevlog.reservation.booking.service.BookingService;
import kr.luciddevlog.reservation.user.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BookingController {
    private final BookingService bookingService;
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("month")
    public String monthStatusPage(Model model) {
        model.addAttribute("monthlySchedule", bookingService.makeMonthlySchedule());
        return"d_01";
    }

    @GetMapping("book-room")
    public String toReservationPage(@RequestParam LocalDate date,
                                    @RequestParam Long roomId,
                                    @AuthenticationPrincipal CustomUserDetails userDetails,
                                    Model model) {
        RoomAndMemberDto dto = bookingService.deliverBookingForm(roomId, userDetails.getUserItem());
        model.addAttribute("rooms", dto.getRoom());
        model.addAttribute("user", dto.getUser());
        model.addAttribute("checkInDate", date);

        // 이미 예약된 날짜 목록을 가져온다.
        LocalDate fastestBookedDate = bookingService.getFastestBookedDateForRoom(roomId, date);
        model.addAttribute("fastestBookedDate", fastestBookedDate);

        return "d_02";
    }

    @PostMapping("calculate-price")
    public ResponseEntity<Map<String, PriceDto>> calculatePrice(@RequestBody PriceRequestDto priceRequestDto) {
        PriceDto priceDto = bookingService.makePriceResponse(priceRequestDto);

        Map<String, PriceDto> response = new HashMap<>();
        response.put("priceDto", priceDto);

        return ResponseEntity.ok(response);
    }
    @PostMapping("booking")
    public void booking(@RequestBody Long roomId, @RequestBody LocalDate checkInDate, @RequestBody String memo, @RequestBody Integer people,
                        @RequestBody LocalDate checkOutDate, @AuthenticationPrincipal CustomUserDetails userDetails){
        BookingFormDto form = BookingFormDto.builder()
                .roomId(roomId)
                .userId(userDetails.getUserItem().getId())
                .memo(memo)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .people(people)
                .build();
        bookingService.makeReservation(form);
    }
}
