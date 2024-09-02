INSERT INTO users(username, password, name, role, createdAt, phoneNumber)
VALUES ('demo', '$2a$10$tEEvNEuRKUXNu2byjEtxyOvcErNxurmdX373UGelVejpTmsububUq', '관리자', 'ROLE_ADMIN', CURRENT_TIMESTAMP(), '010-0000-0000');
INSERT INTO users(username, password, name, role, createdAt, phoneNumber)
VALUES ('kopo', '$2a$12$KIvGwkZtuJ4yljJyXzz0JuJgCzmHVWjnsww30T/W16ZELiKOWBMJ2', '군터', 'ROLE_USER', CURRENT_TIMESTAMP(), '010-1234-1234');

-- NOTICE 게시글 5개 삽입
INSERT INTO board_items (writer_id, content, createdAt, updatedAt, category, title, re_cnt, re_level, view_cnt)
VALUES
(1, '공지사항 내용 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 1', 0, 0, 0),
(1, '공지사항 내용 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 2', 0, 0, 0),
(1, '공지사항 내용 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 3', 0, 0, 0),
(1, '공지사항 내용 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 4', 0, 0, 0),
(1, '공지사항 내용 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 5', 0, 0, 0);

-- REVIEW 게시글 5개 삽입
INSERT INTO board_items (writer_id, content, createdAt, updatedAt, category, title, re_cnt, re_level, view_cnt, score)
VALUES
(1, '리뷰 내용 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 1', 0, 0, 0, 5),
(1, '리뷰 내용 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 2', 0, 0, 0, 4),
(1, '리뷰 내용 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 3', 0, 0, 0, 3),
(1, '리뷰 내용 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 4', 0, 0, 0, 5),
(1, '리뷰 내용 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 5', 0, 0, 0, 4);

-- REVIEW의 첫 번째 게시글에 대한 댓글 5개 삽입
-- 먼저 첫 번째 REVIEW 게시글의 ID를 가져옵니다 (NOTICE 5개 + REVIEW 1개 = 6)
SET @review_id = 6;

-- 댓글 삽입 및 부모 게시글의 re_cnt 업데이트
INSERT INTO board_items (writer_id, content, createdAt, updatedAt, category, root_id, re_cnt, re_level, score)
VALUES
(1, '댓글 내용 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 0, 1, NULL),
(1, '댓글 내용 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 0, 1, NULL),
(1, '댓글 내용 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 0, 1, NULL),
(1, '댓글 내용 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 0, 1, NULL),
(1, '댓글 내용 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 0, 1, NULL);

-- 부모 게시글의 re_cnt 업데이트
UPDATE board_items SET re_cnt = 5 WHERE id = @review_id;