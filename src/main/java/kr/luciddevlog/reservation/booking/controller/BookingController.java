package kr.luciddevlog.reservation.booking.controller;

import kr.luciddevlog.reservation.booking.dto.*;
import kr.luciddevlog.reservation.booking.service.BookingService;
import kr.luciddevlog.reservation.user.entity.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

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
    public String booking(@RequestParam Long roomId, @RequestParam LocalDate checkInDate, @RequestParam String memo, @RequestParam Integer people,
                        @RequestParam LocalDate checkOutDate, @AuthenticationPrincipal CustomUserDetails userDetails){
        BookingFormDto form = BookingFormDto.builder()
                .roomId(roomId)
                .userId(userDetails.getUserItem().getId())
                .memo(memo)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .people(people)
                .build();
        logger.info("Received booking request: {}", form);
        try {
            bookingService.makeReservation(form);
            logger.info("Booking successful for: {}", form);
            return "redirect:/month";
        } catch (Exception e) {
            logger.error("Booking failed for: {}. Error: {}", form, e.getMessage());
            throw e;
        }
    }
}
