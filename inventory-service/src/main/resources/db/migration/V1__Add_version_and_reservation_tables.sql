ALTER TABLE book_inventory
    ADD version BIGINT;

UPDATE book_inventory
SET version = 0;

ALTER TABLE book_inventory MODIFY version BIGINT NOT NULL;


CREATE TABLE reservations
(
    id              NUMBER(19) NOT NULL PRIMARY KEY,
    reservation_uid RAW(16) NOT NULL UNIQUE,
    status          VARCHAR2(255) NOT NULL,
    created_at      TIMESTAMP,
    expires_at      TIMESTAMP
);

CREATE TABLE reservation_items
(
    id             NUMBER(19) NOT NULL PRIMARY KEY,
    reservation_id NUMBER(19),
    isbn           VARCHAR2(255) NOT NULL,
    quantity       NUMBER(10) NOT NULL,
    CONSTRAINT fk_reservation_items_reservation
        FOREIGN KEY (reservation_id)
            REFERENCES reservations (id)
);

CREATE SEQUENCE reservation_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE item_sequence START WITH 1 INCREMENT BY 1;