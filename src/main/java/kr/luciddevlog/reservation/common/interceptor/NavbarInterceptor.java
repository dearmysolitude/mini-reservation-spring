package kr.luciddevlog.reservation.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.luciddevlog.reservation.common.entity.NavItem;
import kr.luciddevlog.reservation.common.entity.SubItem;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Component
public class NavbarInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            modelAndView.addObject("pageTitle", "** 리조트에 오신 것을 환영합니다");
            modelAndView.addObject("navItems", getNavItems());
        }
    }

    private List<NavItem> getNavItems() {
        // NavItems 생성 로직
        return Arrays.asList(
                new NavItem("리조트 소개", Arrays.asList(
                        new SubItem("조아리조트", "/reservation/"), // Controller 로 연결, reservation은 프로젝트이름
                        new SubItem("vip룸", "/reservation/vip"),
                        new SubItem("delux룸", "/reservation/deluxe")
                )),
                new NavItem("찾아오기", List.of(
                        new SubItem("찾아오는 길", "/reservation/location")
                )),
                new NavItem("주변 여행지", List.of(
                        new SubItem("높아산", "/reservation/san"),
                        new SubItem("조아해수욕장", "/reservation/bada")
                ))
        );
    }
}
