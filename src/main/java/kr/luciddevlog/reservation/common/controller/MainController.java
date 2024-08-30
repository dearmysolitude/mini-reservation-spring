package kr.luciddevlog.reservation.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    public String home() {
        return "/home";
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
