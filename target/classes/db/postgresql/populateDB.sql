INSERT INTO book (title, author, description, removed, available_quantity, image)
VALUES
('Przygody Koziołka Matołka' ,'polish guy józek ącki','This book is really great', false, 101, null),
('комп''ютер для літніх людей', 'some ukrainian  guy', 'краща книга коли-небудь', false, 5, null);

INSERT INTO users (username, password, enabled)
VALUES
('library@epam.com', '$2a$10$yvltV50tLunreFCKsMLqz.22o/kB7wlQ.dXQzaoQi6YmilpZXEGAK', TRUE),
('simpleuser@epam.com', '$2a$10$yvltV50tLunreFCKsMLqz.22o/kB7wlQ.dXQzaoQi6YmilpZXEGAK', TRUE),
('maciej_kizlich@epam.com', '$2a$10$yvltV50tLunreFCKsMLqz.22o/kB7wlQ.dXQzaoQi6YmilpZXEGAK', TRUE);


INSERT INTO authority (id, authority )
values (1, 'ROLE_USER'),(2, 'ROLE_ADMIN');


INSERT INTO user_authority (user_id, authority_id)
VALUES
((select id from users where username='library@epam.com'), (select id from authority where authority='ROLE_USER') ),
((select id from users where username='library@epam.com'), (select id from authority where authority='ROLE_ADMIN') ),
((select id from users where username='simpleuser@epam.com'), (select id from authority where authority='ROLE_USER') ),
((select id from users where username='maciej_kizlich@epam.com'), (select id from authority where authority='ROLE_USER') );


INSERT INTO tag (title) VALUES ('Fantasy'), ('Bla Bla Romantic');

INSERT INTO book_order (reservation_date, expected_return_date, status, book_id, user_id) VALUES
  (CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP + interval '2 days',
  'BORROWED',
  1,
  (select id from users where username='maciej_kizlich@epam.com'));
  
INSERT INTO book_order (reservation_date, status, book_id, user_id) VALUES
  (CURRENT_TIMESTAMP,
  'RESERVED',
  1,
  (select id from users where username='library@epam.com'));
  
INSERT INTO library_settings (key, value, description) VALUES 
(('max.loan.period'), ('14'), ('Period in days between borrowing and returning the book ')), 
(('max.reservation.period'), ('14'), ('Period in days between reservation and cancellation of book reservation')),
(('book.return.reminder.in.days'), ('3'), ('Number of days prior to reminder of book return is sent')),
(('block.user.reservation.days.after'), ('7'), ('Number of days after which User reservations will be blocked')),
(('book.return.reminder.in.days.after'), ('3'), ('Number of days after order expired to remind of book return'));