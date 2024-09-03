package kr.luciddevlog.reservation.board.repository;

import kr.luciddevlog.reservation.board.entity.BoardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<BoardItem, Long>, CommentRepositoryCustom {
    List<BoardItem> findAllByRootIdOrderByReCntAsc(Long id);
    int countByRootId(Long id);
    void deleteByRootId(Long id);

    // CommentItem이 BoardItem으로 통합, 고쳐야 함
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE BoardItem c SET c.reCnt = c.reCnt + 1 WHERE c.rootId = :boardId AND c.reCnt >= :reCnt")
    void updateReCntForComment(@Param("boardId") Long boardId, @Param("reCnt") int reCnt);

    @Query("SELECT MIN(c.reCnt) FROM BoardItem c WHERE c.rootId = :boardId AND c.reLevel = :reLevel AND c.reCnt > :reCnt")
    Integer findMinReCnt(@Param("boardId") Long boardId, @Param("reLevel") int reLevel, @Param("reCnt") int reCnt);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE BoardItem c SET c.reCnt = c.reCnt - 1 WHERE c.rootId = :boardId AND c.reCnt >= :reCnt")
    void decreaseReCntForComment(@Param("boardId") Long boardId, @Param("reCnt") int reCnt);
}
