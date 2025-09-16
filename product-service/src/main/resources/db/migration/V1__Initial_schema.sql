CREATE TABLE authors
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    author_name        VARCHAR(255),
    author_description VARCHAR(1000),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE publishers
(
    id                    BIGINT NOT NULL AUTO_INCREMENT,
    publisher_name        VARCHAR(255),
    publisher_description VARCHAR(1000),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE books
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    created_date   DATETIME(6),
    last_edit_date DATETIME(6),
    deleted_date   DATETIME(6),
    isbn           VARCHAR(255),
    title          VARCHAR(255),
    description    VARCHAR(1000),
    publish_year   INTEGER,
    page_count     INTEGER,
    language_code  VARCHAR(255),
    publisher_id   BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_books_publishers FOREIGN KEY (publisher_id) REFERENCES publishers (id)
) ENGINE=InnoDB;

CREATE TABLE book_author
(
    book_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book_author_books FOREIGN KEY (book_id) REFERENCES books (id),
    CONSTRAINT fk_book_author_authors FOREIGN KEY (author_id) REFERENCES authors (id)
) ENGINE=InnoDB;

CREATE TABLE book_category
(
    book_id  BIGINT NOT NULL,
    category VARCHAR(255),
    CONSTRAINT fk_book_category_books FOREIGN KEY (book_id) REFERENCES books (id)
) ENGINE=InnoDB;