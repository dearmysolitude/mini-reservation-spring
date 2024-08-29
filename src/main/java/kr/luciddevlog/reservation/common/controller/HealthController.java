package kr.luciddevlog.reservation.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {
    @GetMapping("/healthy")
    public String healthy(Model model) {
        model.addAttribute("title", "Health Check");
        model.addAttribute("body", "healthy");
        return "/check";
    }
}
