package kr.luciddevlog.reservation.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="UserItem")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 30)
    private Long id;

    @Column(name = "username", length = 30)
    private String username;

    @Column(name = "password", length = 30)
    private String password;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @Column(name="role")
    private String role;

//    @PrePersist //  JPA 엔티티가 데이터베이스에 처음 저장되기 직전에 자동으로 호출
//    protected void onCreate() { // 생성시 시간을 생성하도록 설정
//        createdAt = LocalDate.now();
//    }
}
