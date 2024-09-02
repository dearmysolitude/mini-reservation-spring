package kr.luciddevlog.reservation.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String introduce;

    @Column(nullable = false)
    private String name;
}
