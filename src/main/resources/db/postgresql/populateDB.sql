INSERT INTO users (username, password, enabled)
VALUES
('library@epam.com', '$2a$10$yvltV50tLunreFCKsMLqz.22o/kB7wlQ.dXQzaoQi6YmilpZXEGAK', TRUE),
('simpleuser@epam.com', '$2a$10$yvltV50tLunreFCKsMLqz.22o/kB7wlQ.dXQzaoQi6YmilpZXEGAK', TRUE),
('maciej_kizlich@epam.com', '$2a$10$yvltV50tLunreFCKsMLqz.22o/kB7wlQ.dXQzaoQi6YmilpZXEGAK', TRUE);

INSERT INTO t_companies (name) VALUES
('Nazwa.pl'), ('EPAM Systems');

INSERT INTO t_questions (question, position, ask_date, company_id, user_id) VALUES
('Jak dziala Garbage Collector?', 
 'Java Senior Software Developer', 
  CURRENT_TIMESTAMP, 
  (select id from t_companies where name = 'Nazwa.pl'),
  (select id from users where username = 'maciej_kizlich@epam.com')
),

('Jakie sa roznice miedzy interfejsem a klasa abstrakcyjna?',
'Software Developer',
CURRENT_TIMESTAMP,
  (select id from t_companies where name = 'EPAM Systems'),
  (select id from users where username='maciej_kizlich@epam.com')
);

INSERT INTO t_answers (answer, rating, answer_date, question_id, user_id) VALUES
('normalnie dziala', 
 5, 
 CURRENT_TIMESTAMP, 
 (select id from t_questions where id = 1),
 (select id from users where username = 'maciej_kizlich@epam.com')
),
('interfejs to interfejst, a klasa abstrakcyjna to klasa abstrakcyjna', 
 3, 
 CURRENT_TIMESTAMP, 
 (select id from t_questions where id = 2),
 (select id from users where username = 'maciej_kizlich@epam.com')
),
('interfejs jest lepszy', 
 3, 
 CURRENT_TIMESTAMP, 
 (select id from t_questions where id = 2),
 (select id from users where username = 'library@epam.com')
);

INSERT INTO t_user_messages (topic, message, to_user_id, from_user_id, read) VALUES (
'hej', 
'jakie miales pytania w nazwie?', 
 (select id from users where username = 'maciej_kizlich@epam.com'),
 (select id from users where username = 'library@epam.com'),
 false
);

INSERT INTO book (title, author, description, removed, available_quantity, image)
VALUES
('Przygody Koziołka Matołka' ,'polish guy józek ącki','This book is really great', false, 101, null),
('комп''ютер для літніх людей', 'some ukrainian  guy', 'краща книга коли-небудь', false, 5, null);


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