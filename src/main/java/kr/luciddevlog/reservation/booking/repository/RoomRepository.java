package kr.luciddevlog.reservation.booking.repository;

import kr.luciddevlog.reservation.booking.entity.RoomItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomItem, Long> {
}
