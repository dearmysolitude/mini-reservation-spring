package kr.luciddevlog.reservation.common.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class NavItem {
    private String name;
    private List<SubItem> subItems;

    public NavItem(String name, List<SubItem> subItems) {
        this.name = name;
        this.subItems = subItems;
    }

}

