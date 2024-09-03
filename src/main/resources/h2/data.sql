INSERT INTO users(username, password, name, role, createdAt, phoneNumber)
VALUES ('demo', '$2a$10$tEEvNEuRKUXNu2byjEtxyOvcErNxurmdX373UGelVejpTmsububUq', '관리자', 'ROLE_ADMIN', CURRENT_TIMESTAMP(), '010-0000-0000');
INSERT INTO users(username, password, name, role, createdAt, phoneNumber)
VALUES ('kopo', '$2a$12$KIvGwkZtuJ4yljJyXzz0JuJgCzmHVWjnsww30T/W16ZELiKOWBMJ2', '군터', 'ROLE_USER', CURRENT_TIMESTAMP(), '010-1234-1234');

-- NOTICE 게시글 15개 삽입
INSERT INTO board_items (writer_id, content, createdAt, updatedAt, category, title, re_cnt, re_level, view_cnt)
VALUES
(1, '공지사항 내용 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 1', 0, 0, 0),
(1, '공지사항 내용 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 2', 0, 0, 0),
(1, '공지사항 내용 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 3', 0, 0, 0),
(1, '공지사항 내용 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 4', 0, 0, 0),
(1, '공지사항 내용 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 5', 0, 0, 0),
(1, '공지사항 내용 6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 6', 0, 0, 0),
(1, '공지사항 내용 7', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 7', 0, 0, 0),
(1, '공지사항 내용 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 8', 0, 0, 0),
(1, '공지사항 내용 9', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 9', 0, 0, 0),
(1, '공지사항 내용 10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 10', 0, 0, 0),
(1, '공지사항 내용 11', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 11', 0, 0, 0),
(1, '공지사항 내용 12', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 12', 0, 0, 0),
(1, '공지사항 내용 13', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 13', 0, 0, 0),
(1, '공지사항 내용 14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 14', 0, 0, 0),
(1, '공지사항 내용 15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '공지사항 제목 15', 0, 0, 0);


-- REVIEW 게시글 15개 삽입
INSERT INTO board_items (writer_id, content, createdAt, updatedAt, category, title, re_cnt, re_level, view_cnt, score)
VALUES
(2, '리뷰 내용 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 1', 0, 0, 0, 5),
(2, '리뷰 내용 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 2', 0, 0, 0, 4),
(2, '리뷰 내용 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 3', 0, 0, 0, 3),
(2, '리뷰 내용 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 4', 0, 0, 0, 5),
(2, '리뷰 내용 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 5', 0, 0, 0, 4),
(2, '리뷰 내용 6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 6', 0, 0, 0, 5),
(2, '리뷰 내용 7', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 7', 0, 0, 0, 4),
(2, '리뷰 내용 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 8', 0, 0, 0, 3),
(2, '리뷰 내용 9', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 9', 0, 0, 0, 5),
(2, '리뷰 내용 10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 10', 0, 0, 0, 4),
(2, '리뷰 내용 11', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 11', 0, 0, 0, 5),
(2, '리뷰 내용 12', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 12', 0, 0, 0, 4),
(2, '리뷰 내용 13', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 13', 0, 0, 0, 3),
(2, '리뷰 내용 14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 14', 0, 0, 0, 5),
(2, '리뷰 내용 15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '리뷰 제목 15', 0, 0, 0, 4);

-- REVIEW의 첫 번째 게시글에 대한 댓글 15개 삽입
-- 먼저 첫 번째 REVIEW 게시글의 ID를 가져옵니다 (NOTICE 15개 + REVIEW 15개 = 30)
SET @review_id = 30;

-- 댓글 삽입 및 부모 게시글의 re_cnt 업데이트
INSERT INTO board_items (writer_id, content, createdAt, updatedAt, category, root_id, re_cnt, re_level, score)
VALUES
(1, '댓글 내용 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 1, 1, NULL),
(1, '댓글 내용 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 1, 1, NULL),
(1, '댓글 내용 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 1, 1, NULL),
(1, '댓글 내용 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 1, 1, NULL),
(1, '댓글 내용 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, @review_id, 1, 1, NULL);

-- RoomItem 테이블에 두 개의 방 데이터 삽입
INSERT INTO RoomItem (price, description, name) VALUES
(700000, '아름다운 전망과 편안한 분위기의 더블룸입니다. 커플이나 비즈니스 여행객에게 적합합니다.', 'VIP룸'),
(350000, '넓은 공간과 고급스러운 인테리어의 스위트룸입니다. 가족이나 그룹 여행객에게 이상적입니다.', '디럭스 스위트룸'),
(100000, '탁 트인 바다뷰를 가지고 있는 일반룸입니다. 가족이나 그룹 여행객에게 이상적입니다.', '오션뷰 일반룸');

INSERT INTO BookingItem (user_id, room_id, checkInDate, checkOutDate, people, status, createdAt, updatedAt) VALUES
(2, 1, '2024-09-05', '2024-09-07', 2, 0, '2024-09-03 10:00:00', '2024-09-03 10:00:00'),
(2, 2, '2024-09-08', '2024-09-14', 4, 1, '2024-09-03 11:30:00', '2024-09-03 11:30:00'),
(2, 3, '2024-09-10', '2024-09-11', 1, 0, '2024-09-03 14:15:00', '2024-09-03 14:15:00'),
(2, 2, '2024-09-15', '2024-09-18', 3, 1, '2024-09-04 09:45:00', '2024-09-04 09:45:00'),
(2, 1, '2024-09-20', '2024-09-26', 2, 0, '2024-09-05 16:20:00', '2024-09-05 16:20:00'),
(2, 2, '2024-09-25', '2024-09-27', 5, 1, '2024-09-06 13:10:00', '2024-09-06 13:10:00'),
(2, 1, '2024-09-28', '2024-10-01', 2, 0, '2024-09-07 11:05:00', '2024-09-07 11:05:00'),
(2, 3, '2024-10-01', '2024-10-03', 3, 1, '2024-09-08 15:30:00', '2024-09-08 15:30:00');