package kr.luciddevlog.reservation.board.repository;

import static kr.luciddevlog.reservation.board.entity.QBoardItem.boardItem;
import static kr.luciddevlog.reservation.user.entity.QUserItem.userItem;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.luciddevlog.reservation.board.dto.BoardItemWithAuthorName;
import kr.luciddevlog.reservation.board.entity.BoardCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BoardItemRepositoryCustomImpl implements BoardItemRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BoardItemRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // SELECT *(member객체 빼고), member.userName FROM board ORDER BY root_id DESC, re_cnt ASC LIMIT :startCount, :sizePerPage
    @Override
    public Page<BoardItemWithAuthorName> findBoardItemsByCategory(BoardCategory category, Pageable pageable) {

        JPAQuery<BoardItemWithAuthorName> query = jpaQueryFactory
                .select(Projections.constructor(BoardItemWithAuthorName.class,
                        boardItem.id,
                        boardItem.title,
                        boardItem.content,
                        boardItem.createdAt,
                        boardItem.updatedAt,
                        boardItem.reLevel,
                        boardItem.reCnt,
                        boardItem.rootId,
                        boardItem.viewCnt,
                        userItem.id,
                        userItem.name,
                        boardItem.category
                        ))
                .from(boardItem)
                .leftJoin(boardItem.writer, userItem)
                .where(boardItem.category.eq(category), boardItem.rootId.isNull())
                .orderBy(boardItem.id.desc());

        long total = query.fetchCount();

        List<BoardItemWithAuthorName> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);    }

    public BoardItemWithAuthorName findBoardItemByIdWithUserName(Long id) {
        return jpaQueryFactory
                .select(Projections.constructor(BoardItemWithAuthorName.class,
                        boardItem.id,
                        boardItem.title,
                        boardItem.content,
                        boardItem.createdAt,
                        boardItem.updatedAt,
                        boardItem.reLevel,
                        boardItem.reCnt,
                        boardItem.rootId,
                        boardItem.viewCnt,
                        userItem.id,
                        userItem.name,
                        boardItem.category
                ))
                .from(boardItem)
                .leftJoin(boardItem.writer, userItem)
                .where(boardItem.id.eq(id))
                .fetchOne();
    }

}
