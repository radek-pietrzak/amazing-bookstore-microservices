-- Flyway migration script V2
-- This script adds sample data for books, authors, and publishers.

-- Step 1: Insert authors
-- Using explicit IDs to make linking them to books easier in the next steps.
INSERT INTO authors (id, author_name, author_description) VALUES
                                                              (1, 'J.R.R. Tolkien', 'English writer, poet, philologist, and academic, best known as the author of The Hobbit and The Lord of the Rings.'),
                                                              (2, 'Frank Herbert', 'American science fiction author best known for his 1965 novel Dune and its five sequels.'),
                                                              (3, 'Yuval Noah Harari', 'Israeli public intellectual, historian and professor in the Department of History at the Hebrew University of Jerusalem.'),
                                                              (4, 'Andrzej Sapkowski', 'Polish fantasy writer. He is best known for his book series, The Witcher.');

-- Step 2: Insert publishers
INSERT INTO publishers (id, publisher_name, publisher_description) VALUES
                                                                       (1, 'Allen & Unwin', 'British publishing company founded in 1914.'),
                                                                       (2, 'Chilton Books', 'American publisher known for its automotive manuals and, unexpectedly, for publishing the original Dune.'),
                                                                       (3, 'Dvir', 'An Israeli publishing house, founded in 1924.'),
                                                                       (4, 'SuperNowa', 'Polish publishing house, known for publishing Polish science fiction and fantasy.');

-- Step 3: Insert books, linking them to publishers
INSERT INTO books (id, title, isbn, description, publish_year, page_count, language_code, publisher_id, created_date) VALUES
                                                                                                                          (1, 'The Hobbit', '9780547928227', 'A fantasy novel and children''s book about a hobbit who goes on an unexpected journey.', 1937, 310, 'en', 1, NOW()),
                                                                                                                          (2, 'Dune', '9780441013593', 'A science fiction novel set in the distant future amidst a feudal interstellar society.', 1965, 412, 'en', 2, NOW()),
                                                                                                                          (3, 'Sapiens: A Brief History of Humankind', '9780062316097', 'A book that surveys the history of humankind, from the Stone Age to the twenty-first century.', 2011, 443, 'en', 3, NOW()),
                                                                                                                          (4, 'The Last Wish', '9781473232259', 'The first book in The Witcher series, introducing Geralt of Rivia. It is a collection of short stories.', 1993, 400, 'en', 4, NOW());

-- Step 4: Create many-to-many relationships in the book_author join table
INSERT INTO book_author (book_id, author_id) VALUES
                                                 (1, 1), -- The Hobbit -> J.R.R. Tolkien
                                                 (2, 2), -- Dune -> Frank Herbert
                                                 (3, 3), -- Sapiens -> Yuval Noah Harari
                                                 (4, 4); -- The Last Wish -> Andrzej Sapkowski

-- Step 5: Add categories for each book
INSERT INTO book_category (book_id, category) VALUES
                                                  (1, 'SCIENCE_FICTION_AND_FANTASY'),
                                                  (1, 'CHILDREN_S_BOOKS'),
                                                  (2, 'SCIENCE_FICTION_AND_FANTASY'),
                                                  (2, 'LITERATURE'),
                                                  (3, 'HISTORY'),
                                                  (3, 'NON_FICTION'),
                                                  (3, 'SCIENCE'),
                                                  (4, 'SCIENCE_FICTION_AND_FANTASY');