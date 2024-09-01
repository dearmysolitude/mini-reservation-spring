package kr.luciddevlog.reservation.reservation.repository;

import kr.luciddevlog.reservation.reservation.entity.RoomItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomItem, Long> {
}
