package kr.luciddevlog.reservation.user.controller;

import kr.luciddevlog.reservation.common.controller.GenericController;
import kr.luciddevlog.reservation.user.dto.UserDto;
import kr.luciddevlog.reservation.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController extends GenericController<UserDto, Long, UserService> {

}
