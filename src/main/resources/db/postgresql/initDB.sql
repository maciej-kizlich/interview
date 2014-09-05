DROP TABLE IF EXISTS book_order;
DROP TABLE IF EXISTS book_feedback;
DROP TABLE IF EXISTS book_rating;
DROP TABLE IF EXISTS book_favorite;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS library_settings;
DROP TABLE IF EXISTS async_jobs_errors;

DROP TABLE IF EXISTS t_answers;
DROP TABLE IF EXISTS t_questions;
DROP TABLE IF EXISTS t_companies;

CREATE TABLE t_questions (
  id SERIAL PRIMARY KEY,
  question TEXT,
  position VARCHAR(255),
  ask_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  company_id INTEGER,
  user_id INTEGER
);

CREATE TABLE t_companies (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255)
);

CREATE TABLE t_answers (
  id SERIAL PRIMARY KEY,
  answer TEXT,
  rating INTEGER,
  answer_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  question_id INTEGER,
  user_id INTEGER
);

CREATE TABLE book (
  id SERIAL PRIMARY KEY,
  title VARCHAR(200),
  author VARCHAR(200),
  description VARCHAR(500),
  removed BOOLEAN,
  available_quantity INTEGER NOT NULL,
  image bytea
);

CREATE INDEX books_title ON book (title);


CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL,
  enabled  BOOLEAN     NOT NULL,
  user_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_login_date TIMESTAMP,
  reservation_blocked BOOLEAN DEFAULT false
  );

CREATE TABLE book_order (
  id SERIAL PRIMARY KEY,
  reservation_date TIMESTAMP,
  borrowed_date TIMESTAMP,
  expected_return_date TIMESTAMP,
  status VARCHAR(20),
  returned_date TIMESTAMP,
  book_id INTEGER,
  user_id INTEGER,

  CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE authority (
  id SERIAL PRIMARY KEY,
  authority VARCHAR(50) NOT NULL

);

CREATE UNIQUE INDEX ix_auth_username ON authority (id, authority);

CREATE TABLE user_authority (
    user_id INTEGER NOT NULL,
    authority_id INTEGER NOT NULL,

    CONSTRAINT fk_authorities_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_authorities_authorities FOREIGN KEY (authority_id) REFERENCES authority (id)
);

CREATE UNIQUE INDEX ix_user_authority ON user_authority(user_id, authority_id);

CREATE TABLE tag (
  id    SERIAL  PRIMARY KEY,
  title VARCHAR(30)
);

CREATE TABLE book_feedback (
  id SERIAL PRIMARY KEY,
  book_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  message VARCHAR(500),
  created_date TIMESTAMP,

  CONSTRAINT fk_book_feedback FOREIGN KEY (book_id) REFERENCES book (id),
  CONSTRAINT fk_user_feedback FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE library_settings (
key varchar(255) PRIMARY KEY,
value varchar(255),
description varchar(255)
);

CREATE TABLE book_rating (
  id SERIAL PRIMARY KEY,
  book_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  rating INTEGER NOT NULL,
  created_date TIMESTAMP,

  CONSTRAINT fk_book_rating FOREIGN KEY (book_id) REFERENCES book (id),
  CONSTRAINT fk_user_rating FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE book_favorite (
  id SERIAL PRIMARY KEY,
  book_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  created_date TIMESTAMP,

  CONSTRAINT fk_book_favorite FOREIGN KEY (book_id) REFERENCES book (id),
  CONSTRAINT fk_user_favorite FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE async_jobs_errors (
id SERIAL PRIMARY KEY,
date_of_occuring TIMESTAMP,
job_name VARCHAR(500),
last_exception VARCHAR(500),
initial_exception VARCHAR(500),
initial_message VARCHAR(500)
);

