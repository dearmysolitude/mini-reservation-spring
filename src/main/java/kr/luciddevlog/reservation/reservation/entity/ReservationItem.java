//package kr.luciddevlog.reservation.reservation.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import kr.luciddevlog.reservation.member.entity.UserItem;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class ReservationItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn
//    @JsonBackReference // 순환참조 방지: DTO로 넘겨주는게 바람직하다.
//    private UserItem user;
//
//    @ManyToOne
//    @JoinColumn
//    @JsonBackReference
//    private RoomItem roomItem;
//
//    @Column
//    private LocalDate checkInDate;
//
//    @Column
//    private int people;
//
//    //Enum
//    @Column
//    private Enum
//}
