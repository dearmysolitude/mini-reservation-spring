package kr.luciddevlog.reservation.board.repository;

import static kr.luciddevlog.reservation.board.entity.QBoardItem.boardItem;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BoardItemRespositoryCustomImpl implements BoardItemRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BoardItemRespositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // SELECT *(member객체 빼고), member.userName FROM board ORDER BY root_id DESC, re_cnt ASC LIMIT :startCount, :sizePerPage
    @Override
    public Page<BoardItemWithAuthorName> findBoardItemsByCategory(BoardCategory category, Pageable pageable) {
        List<BoardItemWithAuthorName> content = jpaQueryFactory
                .select(Projections.constructor(BoardItemWithAuthorName.class,
                        boardItem.id,
                        boardItem.title,
                        boardItem.content,
                        boardItem.createdAt,
                        boardItem.rootId,
                        boardItem.reCnt,
                        boardItem.reLevel,
                        boardItem.viewCnt,
                        boardItem.writer.name))
                .from(boardItem)
                .leftJoin(boardItem.writer)
                .where(boardItem.category.eq(category))  // 카테고리 조건 추가
                .orderBy(boardItem.rootId.desc(), boardItem.reCnt.asc(), boardItem.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(boardItem)
                .where(boardItem.category.eq(category))  // 카테고리 조건 추가
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    public BoardItemWithAuthorName findBoardItemById(Long id) {
        return jpaQueryFactory
                .select(Projections.constructor(BoardItemWithAuthorName.class,
                        boardItem.id,
                        boardItem.title,
                        boardItem.content,
                        boardItem.createdAt,
                        boardItem.rootId,
                        boardItem.viewCnt,
                        boardItem.writer.name))
                .from(boardItem)
                .leftJoin(boardItem.writer)
                .where(boardItem.id.eq(id))
                .fetchOne();
    }
}
