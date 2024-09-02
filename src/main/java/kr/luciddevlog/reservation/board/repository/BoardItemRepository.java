package kr.luciddevlog.reservation.board.repository;

import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import kr.luciddevlog.reservation.board.entity.BoardItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// Refactor: QueryDSL로 변경
@Repository
public interface BoardItemRepository extends JpaRepository<BoardItem, Long>, BoardItemRepositoryCustom {
    Long countByCategory(BoardCategory boardCategory);
    Long countByCategoryAndRootIdIsNull(BoardCategory boardCategory);

    @Modifying
    @Query(value = "UPDATE BoardItem SET viewCnt = viewCnt + 1 WHERE id = :id", nativeQuery = true)
    void incrementViewCount(@Param("id") Long id);
}
