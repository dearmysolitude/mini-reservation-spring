package kr.luciddevlog.reservation.reservation.repository;

import kr.luciddevlog.reservation.reservation.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationItem, Long> {
}
