package kr.luciddevlog.reservation.common.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubItem {
    private String name;
    private String link;

    public SubItem(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
