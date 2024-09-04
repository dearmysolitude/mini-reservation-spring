package kr.luciddevlog.reservation.board.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.luciddevlog.reservation.board.dto.CommentItemWithAuthorName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static kr.luciddevlog.reservation.board.entity.QBoardItem.boardItem;
import static kr.luciddevlog.reservation.user.entity.QUserItem.userItem;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public CommentRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // SELECT *(member객체 빼고), member.userName FROM board ORDER BY root_id DESC, re_cnt ASC LIMIT :startCount, :sizePerPage
    @Override
    public List<CommentItemWithAuthorName> findCommentItemsByRootId (Long rootId) {
        return jpaQueryFactory
                .select(Projections.constructor(CommentItemWithAuthorName.class,
                        boardItem.id,
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
                .where(boardItem.rootId.eq(rootId))
                .orderBy(boardItem.reCnt.asc())
                .fetch();
    }
}
