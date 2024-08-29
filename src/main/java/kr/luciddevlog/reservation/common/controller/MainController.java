package kr.luciddevlog.reservation.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "어서오세요!");
        model.addAttribute("body", "healthy");
        return "/static/home";
    }
}
