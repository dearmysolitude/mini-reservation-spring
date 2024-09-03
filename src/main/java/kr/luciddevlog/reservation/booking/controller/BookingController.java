package kr.luciddevlog.reservation.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {
    @GetMapping("month")
    public String monthStatusPage() {

        return"d_01";
    }

    @GetMapping("/form")
    public String toReservationPage() {

        return"d_02";
    }
}
