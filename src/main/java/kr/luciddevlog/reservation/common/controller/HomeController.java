package kr.luciddevlog.reservation.common.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("user", userDetails);
        }
        // 기타 필요한 모델 속성 추가
        return "home";
    }

    @RequestMapping("/vip")
    public String vipRoom() {
        return "/a_01";
    }

    @RequestMapping("/deluxe")
    public String regularRoom() {
        return "/a_02";
    }

    @RequestMapping("/location")
    public String location() {
        return "/b_01";
    }

    @RequestMapping("/san")
    public String nearSpot1() {
        return "/c_01";
    }

    @RequestMapping("/bada")
    public String nearSpot2() {
        return "/c_02";
    }

}
