package kr.luciddevlog.reservation.user.repository;

import kr.luciddevlog.reservation.user.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    UserItem findByUsername(String username);
}
